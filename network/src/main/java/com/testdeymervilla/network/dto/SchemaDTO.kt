package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class SchemaDTO(
    @SerializedName("NombreTabla")
    val tableName: String? = null,
    @SerializedName("Pk")
    val primaryKey: String?,
    @SerializedName("QueryCreacion")
    val queryCreation: String?,
    @SerializedName("BatchSize")
    val batchSize: Int?,
    @SerializedName("Filtro")
    val filter: String?,
    @SerializedName("Error")
    val error: String?,
    @SerializedName("NumeroCampos")
    val fieldsNumber: Int?,
    @SerializedName("MetodoApp")
    val appMethod: String?,
    @SerializedName("FechaActualizacionSincro")
    val updatedAt: String?
)