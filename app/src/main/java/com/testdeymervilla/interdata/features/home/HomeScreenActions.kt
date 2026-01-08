package com.testdeymervilla.interdata.features.home

data class HomeScreenActions(
    val onPrimaryAction: () -> Unit,
    val onSecondaryAction: () -> Unit
)