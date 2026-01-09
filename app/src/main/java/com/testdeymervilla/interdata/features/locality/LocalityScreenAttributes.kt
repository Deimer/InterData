package com.testdeymervilla.interdata.features.locality

import androidx.compose.material3.SnackbarHostState

data class LocalityScreenAttributes(
    val localityId: Int,
    val actions: LocalityScreenActions,
    val snackbarHostState: SnackbarHostState
)