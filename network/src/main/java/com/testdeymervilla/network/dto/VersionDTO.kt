package com.testdeymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class VersionDTO(
    @SerializedName("version")
    val version: String? = null
)