package com.testdeymervilla.network.dto.response

import com.google.gson.annotations.SerializedName

data class BaseResponseDTO<T>(
    @SerializedName("Codigo")
    val code: String? = null,
    @SerializedName("Mensaje")
    val message: String? = null,
    @SerializedName("Data")
    val data: T
)