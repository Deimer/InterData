package com.testdeymervilla.repository.repositories.schema

import com.testdeymervilla.datasource.local.schema.ISchemaLocalDataSource
import com.testdeymervilla.datasource.remote.schema.ISchemaRemoteDataSource
import com.testdeymervilla.repository.mappers.toDomain
import com.testdeymervilla.repository.mappers.toEntity
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class SchemaRepository @Inject constructor(
    private val schemaLocalDataSource: ISchemaLocalDataSource,
    private val schemaRemoteDataSource: ISchemaRemoteDataSource
): ISchemaRepository {

    override fun getSchemas() = flow {
        try {
            val schemas = schemaLocalDataSource.fetch().map { it.toDomain() }.ifEmpty {
                val newSchemas = schemaRemoteDataSource.fetch()
                schemaLocalDataSource.insert(newSchemas.map { it.toEntity() })
                schemaLocalDataSource.fetch().map { it.toDomain() }
            }
            emit(value = Result.success(schemas))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun getSchema(
        schemaId: Int
    ) = flow {
        try {
            val schema = schemaLocalDataSource.fetchById(schemaId).toDomain()
            emit(Result.success(schema))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun clearSchema(
        schemaId: Int
    ) = flow {
        try {
            emit(value = Result.success(schemaLocalDataSource.clearById(schemaId)))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun clearSchemas() = flow {
        try {
            emit(value = Result.success(schemaLocalDataSource.clear()))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }
}