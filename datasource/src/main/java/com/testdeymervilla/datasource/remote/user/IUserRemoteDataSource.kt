package com.testdeymervilla.datasource.remote.user

import com.testdeymervilla.network.dto.UserDTO

interface IUserRemoteDataSource {

    suspend fun getVersion(): String

    suspend fun login(
        username: String,
        password: String
    ): UserDTO
}