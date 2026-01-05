package com.testdeymervilla.presentation.models

data class ItemUiModel(
    val id: Int = 0,
    val startIcon: Int? = null,
    val title: String = "",
    val description: String = "",
    val endIcon: Int? = null,
    var onClick: () -> Unit = {},
)