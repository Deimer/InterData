package com.testdeymervilla.repository.repositories.locality

import com.testdeymervilla.datasource.local.locality.ILocalityLocalDataSource
import com.testdeymervilla.datasource.remote.locality.ILocalityRemoteDataSource
import com.testdeymervilla.repository.mappers.toDomain
import com.testdeymervilla.repository.mappers.toEntity
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import kotlin.collections.ifEmpty

class LocalityRepository @Inject constructor(
    private val localityLocalDataSource: ILocalityLocalDataSource,
    private val localityRemoteDataSource: ILocalityRemoteDataSource
): ILocalityRepository {

    override fun getLocalities() = flow {
        try {
            val localities = localityLocalDataSource.fetch().map { it.toDomain() }.ifEmpty {
                val newLocalities = localityRemoteDataSource.fetch()
                localityLocalDataSource.insert(newLocalities.map { it.toEntity() })
                localityLocalDataSource.fetch().map { it.toDomain() }
            }
            emit(value = Result.success(localities))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun getLocality(
        localityId: Int
    ) = flow {
        try {
            val locality = localityLocalDataSource.fetchById(localityId).toDomain()
            emit(Result.success(locality))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun clearLocality(
        localityId: Int
    ) = flow {
        try {
            emit(value = Result.success(localityLocalDataSource.clearById(localityId)))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun clearLocalities() = flow {
        try {
            emit(value = Result.success(localityLocalDataSource.clear()))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }
}