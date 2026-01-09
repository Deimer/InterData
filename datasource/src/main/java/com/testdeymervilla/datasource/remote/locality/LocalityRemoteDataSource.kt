package com.testdeymervilla.datasource.remote.locality

import com.testdeymervilla.network.api.ApiService
import javax.inject.Inject

class LocalityRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): ILocalityRemoteDataSource {

    override suspend fun fetch() =
        apiService.getLocalities()
}