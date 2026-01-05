package com.testdeymervilla.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.theme.IndianRed
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.theme.White80
import com.testdeymervilla.presentation.theme.tagButton
import kotlinx.coroutines.launch

@Composable
fun SnackBarCompose(
    snackbarHostState: SnackbarHostState,
    shape: Shape = RoundedCornerShape(dimensionResource(id = R.dimen.dimen_8))
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Snackbar(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8)),
                containerColor = IndianRed,
                contentColor = IndianRed,
                shape = shape,
            ) {
                Text(text = snackbarData.visuals.message, style = tagButton)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SnackBarComposePreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    InterDataTheme {
        Scaffold(
            snackbarHost = { SnackBarCompose(snackbarHostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize()
        ) { contentPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { coroutineScope.launch {
                        snackbarHostState.showSnackbar("This is a custom snackbar!")
                    }
                    }) {
                    Text("Show Snackbar", color = White80)
                }
            }
        }
    }
}