package com.testdeymervilla.datasource.local.schema

import com.testdeymervilla.database.dao.SchemaDao
import com.testdeymervilla.database.entities.SchemaEntity
import javax.inject.Inject

class SchemaLocalDataSource @Inject constructor(
    private val schemaDao: SchemaDao
): ISchemaLocalDataSource {

    override suspend fun insert(schemas: List<SchemaEntity>): Boolean {
        val result = schemaDao.insert(schemas)
        return result.isNotEmpty() && result.all { it > 0 }
    }

    override suspend fun fetch() =
        schemaDao.fetch()

    override suspend fun fetchById(schemaId: Int) =
        schemaDao.fetchById(schemaId)

    override suspend fun clear() =
        schemaDao.clear() > 0

    override suspend fun clearById(schemaId: Int) =
        schemaDao.clearById(schemaId) == 1
}