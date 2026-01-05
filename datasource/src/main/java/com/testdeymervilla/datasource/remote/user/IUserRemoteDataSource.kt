package com.testdeymervilla.datasource.remote.user

import com.testdeymervilla.network.dto.response.LoginResponseDTO

interface IUserRemoteDataSource {

    suspend fun login(
        username: String,
        password: String
    ): LoginResponseDTO
}