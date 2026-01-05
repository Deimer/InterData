package com.testdeymervilla.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.models.ItemUiModel
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.utils.dummyItems

@Composable
fun ItemListCompose(
    modifier: Modifier = Modifier,
    items: List<ItemUiModel>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(R.dimen.dimen_12),
        ),
    ) {
        itemsIndexed(items) { index, item ->
            ItemCompose(item)
            DividerCompose(isVisible = index < items.lastIndex)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListComposePreview() {
    InterDataTheme {
        ItemListCompose(items = dummyItems())
    }
}