package com.testdeymervilla.datasource.remote.schema

import com.testdeymervilla.network.dto.SchemaTableDTO

interface ISchemaRemoteDataSource {

    suspend fun fetch(): List<SchemaTableDTO>
}