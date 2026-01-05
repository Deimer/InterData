package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class SchemaTableDTO(
    @SerializedName("NombreTabla")
    val tableName: String? = null,
    @SerializedName("Tabla")
    val fallbackTableName: String? = null,
    @SerializedName("Campos")
    val fields: List<SchemaFieldDTO>? = emptyList()
)