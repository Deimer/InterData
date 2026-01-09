package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("Usuario")
    val username: String? = null,
    @SerializedName("Identificacion")
    val identification: String? = null,
    @SerializedName("Nombre")
    val fullName: String? = null
)