package com.testdeymervilla.repository.domain

data class SchemaDomain(
    val id: Int,
    val tableName: String,
    val fieldsCount: Int,
    val updatedAt: String,
    val primaryKey: String,
    val batchSize: Int
)