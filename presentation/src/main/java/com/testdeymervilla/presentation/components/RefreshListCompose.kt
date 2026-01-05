package com.testdeymervilla.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testdeymervilla.presentation.theme.InterDataTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshListCompose(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    orientation: Orientation = Orientation.Vertical,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = onRefresh
    ) {
        when (orientation) {
            Orientation.Vertical -> Column(
                modifier = Modifier.fillMaxSize()
            ) {
                content()
            }
            Orientation.Horizontal -> Row(
                modifier = Modifier.fillMaxSize()
            ) {
                content()
            }
        }
    }
}

@Preview(
    name = "List – Vertical",
    showBackground = true
)
@Composable
private fun RefreshListVerticalPreview() {
    InterDataTheme {
        RefreshListCompose(
            isRefreshing = false,
            onRefresh = {}
        ) {
            Column {
                repeat(10) { i ->
                    Text(
                        text = "Item ${i + 1}",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(
    name = "List – Horizontal",
    showBackground = true
)
@Composable
private fun RefreshListHorizontalPreview() {
    InterDataTheme {
        RefreshListCompose(
            isRefreshing = false,
            onRefresh = {},
            orientation = Orientation.Horizontal
        ) {
            Row {
                repeat(8) { i ->
                    Text(
                        text = "Card ${i + 1}",
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }
}