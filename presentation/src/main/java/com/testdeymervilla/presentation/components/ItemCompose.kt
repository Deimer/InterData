package com.testdeymervilla.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.models.ItemUiModel
import com.testdeymervilla.presentation.theme.CyanSkyDark
import com.testdeymervilla.presentation.theme.InterDataTheme

@Composable
fun ItemCompose(
    itemUi: ItemUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = itemUi.onClick)
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dimen_2),
                bottom = dimensionResource(id = R.dimen.dimen_4)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemUi.startIcon?.let { icon ->
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dimen_46))
                    .padding(
                        end = dimensionResource(id = R.dimen.dimen_12),
                    ),
                tint = CyanSkyDark
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = dimensionResource(id = R.dimen.dimen_12),
                    bottom = dimensionResource(id = R.dimen.dimen_12)
                )
        ) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = itemUi.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = itemUi.description,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        itemUi.endIcon?.let { icon ->
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dimen_32))
                    .padding(start = dimensionResource(id = R.dimen.dimen_12)),
                tint = CyanSkyDark
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemComposePreview() {
    val simpleItem = ItemUiModel(
        startIcon = android.R.drawable.star_on,
        title = "Title",
        description = "Description",
        endIcon = android.R.drawable.ic_secure,
    ) {}
    InterDataTheme {
        ItemCompose(
            itemUi = simpleItem,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16))
        )
    }
}