package com.testdeymervilla.datasource.remote.schema

import com.testdeymervilla.network.dto.SchemaDTO

interface ISchemaRemoteDataSource {

    suspend fun fetch(): List<SchemaDTO>
}