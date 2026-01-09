package com.testdeymervilla.datasource.remote.locality

import com.testdeymervilla.network.dto.LocalityDTO

interface ILocalityRemoteDataSource {

    suspend fun fetch(): List<LocalityDTO>
}