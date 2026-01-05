package com.testdeymervilla.network.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("Usuario")
    val username: String? = null,
    @SerializedName("Identificacion")
    val identification: String? = null,
    @SerializedName("Nombre")
    val fullName: String? = null
)