package com.testdeymervilla.repository.repositories.schema

import com.testdeymervilla.repository.domain.SchemaDomain
import kotlinx.coroutines.flow.Flow

interface ISchemaRepository {

    fun getSchemas(): Flow<Result<List<SchemaDomain>>>

    fun getSchema(schemaId: Int): Flow<Result<SchemaDomain>>

    fun clearSchema(schemaId: Int): Flow<Result<Boolean>>

    fun clearSchemas(): Flow<Result<Boolean>>
}