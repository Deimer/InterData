package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class FrameworkParameterDTO(
    @SerializedName("Nombre")
    val name: String? = null,
    @SerializedName("Codigo")
    val code: String? = null,
    @SerializedName("Valor")
    val value: String? = null,
    @SerializedName("Descripcion")
    val description: String? = null
)