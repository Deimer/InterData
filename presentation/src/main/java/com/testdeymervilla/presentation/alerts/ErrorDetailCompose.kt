package com.testdeymervilla.presentation.alerts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.theme.InterDataTheme
import kotlinx.coroutines.launch

@Composable
fun ErrorDetailCompose(
    errorMessage: String? = null,
    snackbarHostState: SnackbarHostState
) {
    val snackbarScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        snackbarScope.launch {
            val message = errorMessage ?: context.getString(R.string.error_generic)
            snackbarHostState.showSnackbar(message = message)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorDetailComposePreview() {
    InterDataTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                ErrorDetailCompose(
                    errorMessage = "Test message for error details",
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}