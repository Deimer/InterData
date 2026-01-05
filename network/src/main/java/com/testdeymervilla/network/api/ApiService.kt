package com.testdeymervilla.network.api

import com.testdeymervilla.network.constants.NetworkConstants
import com.testdeymervilla.network.dto.FrameworkParameterDTO
import com.testdeymervilla.network.dto.LocalityDTO
import com.testdeymervilla.network.dto.LoginRequestDTO
import com.testdeymervilla.network.dto.SchemaTableDTO
import com.testdeymervilla.network.dto.response.BaseResponseDTO
import com.testdeymervilla.network.dto.response.LoginResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(NetworkConstants.Paths.Framework.APP_VERSION)
    suspend fun getAppVersion(): BaseResponseDTO<List<FrameworkParameterDTO>>

    @GET(NetworkConstants.Paths.Framework.LOCALITIES)
    suspend fun getLocalities(): BaseResponseDTO<List<LocalityDTO>>

    @GET(NetworkConstants.Paths.Synchronization.SCHEMA)
    suspend fun getSchema(): BaseResponseDTO<List<SchemaTableDTO>>

    @POST(NetworkConstants.Paths.Security.LOGIN)
    suspend fun login(
        @Body request: LoginRequestDTO
    ): BaseResponseDTO<LoginResponseDTO>
}