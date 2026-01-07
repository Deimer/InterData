package com.testdeymervilla.interdata.features.splash

data class SplashScreenActions(
    val onPrimaryAction: () -> Unit,
    val onSecondaryAction: () -> Unit
)