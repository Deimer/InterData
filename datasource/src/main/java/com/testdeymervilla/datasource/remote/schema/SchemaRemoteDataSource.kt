package com.testdeymervilla.datasource.remote.schema

import com.testdeymervilla.network.api.ApiService
import javax.inject.Inject

class SchemaRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): ISchemaRemoteDataSource {

    override suspend fun fetch() =
        apiService.getSchemas()
}