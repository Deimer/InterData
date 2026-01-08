package com.testdeymervilla.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.theme.InterDataTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCompose(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    navigationIcon: Int? = null,
    onNavigationClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
    ) {
        TopAppBar(
            title = { Text(title, style = MaterialTheme.typography.headlineLarge) },
            modifier = modifier.padding(top = dimensionResource(id = R.dimen.dimen_10)),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            navigationIcon = {
                if (navigationIcon != null && onNavigationClick != null) {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            painter = painterResource(id = navigationIcon),
                            contentDescription = ""
                        )
                    }
                }
            }
        )
        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.dimen_16),
                    end = dimensionResource(id = R.dimen.dimen_16),
                    bottom = dimensionResource(id = R.dimen.dimen_12)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTopBarPreview() {
    InterDataTheme {
        TopBarCompose(
            title = "Title",
            subtitle = "Subtitle",
        )
    }
}