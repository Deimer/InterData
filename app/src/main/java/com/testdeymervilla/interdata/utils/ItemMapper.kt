package com.testdeymervilla.interdata.utils

import androidx.annotation.DrawableRes
import com.testdeymervilla.presentation.models.ItemUiModel
import com.testdeymervilla.repository.domain.SchemaDomain

fun List<SchemaDomain>.toItem(
    @DrawableRes startIconRes: Int? = null,
    @DrawableRes endIconRes: Int? = null,
    onItemClick: (Int) -> Unit = {}
): List<ItemUiModel> = map { item ->
    ItemUiModel(
        startIcon = startIconRes,
        title = item.tableName,
        description = item.updatedAt,
        endIcon = endIconRes,
        onClick = { onItemClick(item.id) }
    )
}