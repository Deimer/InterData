package com.testdeymervilla.datasource.remote.schema

import com.testdeymervilla.network.api.ApiService
import com.testdeymervilla.network.dto.SchemaDTO
import javax.inject.Inject

class SchemaRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): ISchemaRemoteDataSource {

    override suspend fun fetch(): List<SchemaDTO> {
        return apiService.getSchema()
    }
}