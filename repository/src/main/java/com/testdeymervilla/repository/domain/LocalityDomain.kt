package com.testdeymervilla.repository.domain

data class LocalityDomain(
    val id: Int,
    val name: String,
    val fullName: String,
    val cityAbbreviation: String,
    val zipCode: String,
    val pickupValue: String
)