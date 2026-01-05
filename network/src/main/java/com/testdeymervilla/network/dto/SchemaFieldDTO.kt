package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class SchemaFieldDTO(
    @SerializedName("Nombre")
    val name: String? = null,
    @SerializedName("Tipo")
    val type: String? = null,
    @SerializedName("Longitud")
    val length: Int? = null,
    @SerializedName("Requerido")
    val isRequired: Boolean? = null
)