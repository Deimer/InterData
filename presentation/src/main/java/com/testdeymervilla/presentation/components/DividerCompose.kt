package com.testdeymervilla.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.theme.Gray40
import com.testdeymervilla.presentation.theme.InterDataTheme

@Composable
fun DividerCompose(
    modifier: Modifier = Modifier,
    color: Color = Gray40,
    thickness: Dp = dimensionResource(id = R.dimen.dimen_1),
    topPadding: Dp = dimensionResource(id = R.dimen.dimen_8),
    bottomPadding: Dp = dimensionResource(id = R.dimen.dimen_8),
    isVisible: Boolean = true
) {
    if (isVisible) {
        HorizontalDivider(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = topPadding,
                    bottom = bottomPadding
                ),
            color = color,
            thickness = thickness
        )
    }
}

@Preview(name = "Divider – Light", showBackground = true)
@Composable
fun DividerComposeLightPreview() {
    InterDataTheme {
        Column {
            DividerCompose(
                thickness = dimensionResource(id = R.dimen.dimen_1),
                topPadding = dimensionResource(id = R.dimen.dimen_8),
                bottomPadding = dimensionResource(id = R.dimen.dimen_8)
            )
        }
    }
}

@Preview(
    name = "Divider – Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun DividerComposeDarkPreview() {
    InterDataTheme {
        Column {
            DividerCompose(
                color = Color(0xFFD6D6D6),
                thickness = dimensionResource(id = R.dimen.dimen_1),
                topPadding = dimensionResource(id = R.dimen.dimen_8),
                bottomPadding = dimensionResource(id = R.dimen.dimen_8)
            )
        }
    }
}