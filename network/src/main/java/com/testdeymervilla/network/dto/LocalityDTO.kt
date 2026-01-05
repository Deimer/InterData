package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class LocalityDTO(
    @SerializedName("AbreviacionCiudad")
    val cityAbbreviation: String? = null,
    @SerializedName("NombreCompleto")
    val fullName: String? = null
)