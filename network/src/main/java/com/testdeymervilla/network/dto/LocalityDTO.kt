package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class LocalityDTO(
    @SerializedName("AbreviacionCiudad")
    val cityCode: String? = null,
    @SerializedName("NombreCompleto")
    val fullName: String? = null,
    @SerializedName("Nombre")
    var name: String? = null,
    @SerializedName("CodigoPostal")
    var postalCode: String? = null,
    @SerializedName("ValorRecogida")
    var amount: Float? = null
)