package com.testdeymervilla.presentation.utils

import com.testdeymervilla.presentation.models.ItemUiModel

fun dummyItems() = listOf(
    ItemUiModel(
        startIcon = android.R.drawable.ic_menu_call,
        title = "Title one",
        description = "Description one",
        endIcon = android.R.drawable.ic_menu_call,
        onClick = {}
    ),
    ItemUiModel(
        startIcon = android.R.drawable.ic_menu_compass,
        title = "Title two",
        description = "Description two",
        endIcon = android.R.drawable.ic_menu_call,
        onClick = {}
    ),
    ItemUiModel(
        startIcon = android.R.drawable.ic_menu_info_details,
        title = "Title three",
        description = "Description three",
        endIcon = android.R.drawable.ic_menu_call,
        onClick = {}
    )
)