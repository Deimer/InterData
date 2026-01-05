package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class LoginRequestDTO(
    @SerializedName("Mac")
    val macAddress: String? = null,
    @SerializedName("NomAplicacion")
    val applicationName: String? = null,
    @SerializedName("Password")
    val password: String? = null,
    @SerializedName("Path")
    val path: String? = null,
    @SerializedName("Usuario")
    val username: String? = null
)