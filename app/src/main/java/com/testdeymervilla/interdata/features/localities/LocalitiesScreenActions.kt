package com.testdeymervilla.interdata.features.localities

data class LocalitiesScreenActions(
    val onPrimaryAction: () -> Unit,
    val onSecondaryAction: (localityId: Int) -> Unit
)