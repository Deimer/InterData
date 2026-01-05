package com.testdeymervilla.datasource.remote.user

import com.testdeymervilla.network.api.ApiService
import com.testdeymervilla.network.dto.request.LoginRequestDTO
import com.testdeymervilla.network.dto.UserDTO
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): IUserRemoteDataSource {

    override suspend fun getVersion() =
        apiService.getAppVersion().data.version.orEmpty()

    override suspend fun login(
        username: String,
        password: String
    ): UserDTO {
        val loginRequest = LoginRequestDTO(
            username = username,
            password = password
        )
        return apiService.login(loginRequest).data
    }
}