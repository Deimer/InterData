package com.testdeymervilla.interdata.features.schema

import androidx.compose.material3.SnackbarHostState

data class SchemaScreenAttributes(
    val schemaId: Int,
    val actions: SchemaScreenActions,
    val snackbarHostState: SnackbarHostState
)
