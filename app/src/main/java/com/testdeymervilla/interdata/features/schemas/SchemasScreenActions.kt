package com.testdeymervilla.interdata.features.schemas

data class SchemasScreenActions(
    val onPrimaryAction: () -> Unit,
    val onSecondaryAction: (schemaId: Int) -> Unit
)