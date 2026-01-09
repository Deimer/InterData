package com.testdeymervilla.datasource.local.schema

import com.testdeymervilla.database.entities.SchemaEntity

interface ISchemaLocalDataSource {

    suspend fun insert(
        schemas: List<SchemaEntity>
    ): Boolean

    suspend fun fetch(): List<SchemaEntity>

    suspend fun fetchById(
        schemaId: Int
    ): SchemaEntity

    suspend fun clear(): Boolean

    suspend fun clearById(
        schemaId: Int
    ): Boolean
}