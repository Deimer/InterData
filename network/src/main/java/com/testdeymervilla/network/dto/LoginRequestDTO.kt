package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class LoginRequestDTO(
    @SerializedName("Mac")
    val macAddress: String = "",
    @SerializedName("NomAplicacion")
    val applicationName: String = "Controller APP",
    @SerializedName("Path")
    val path: String = "",
    @SerializedName("Usuario")
    val username: String,
    @SerializedName("Password")
    val password: String,
)