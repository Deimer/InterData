package com.testdeymervilla.network.api

import com.testdeymervilla.network.constants.NetworkConstants
import com.testdeymervilla.network.dto.VersionDTO
import com.testdeymervilla.network.dto.LocalityDTO
import com.testdeymervilla.network.dto.request.LoginRequestDTO
import com.testdeymervilla.network.dto.SchemaDTO
import com.testdeymervilla.network.dto.response.BaseResponseDTO
import com.testdeymervilla.network.dto.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(NetworkConstants.Paths.Framework.APP_VERSION)
    suspend fun getAppVersion(): String?

    @GET(NetworkConstants.Paths.Framework.LOCALITIES)
    suspend fun getLocalities(): List<LocalityDTO>

    @GET(NetworkConstants.Paths.Synchronization.SCHEMA)
    suspend fun getSchema(): List<SchemaDTO>

    @POST(NetworkConstants.Paths.Security.LOGIN)
    suspend fun login(
        @Body request: LoginRequestDTO
    ): UserDTO
}