package com.testdeymervilla.interdata.features.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.testdeymervilla.interdata.navigation.AppNavigation
import com.testdeymervilla.presentation.theme.InterDataTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            InterDataTheme {
                AppNavigation(snackbarHostState = snackbarHostState)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun InterDataAppPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    InterDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            AppNavigation(snackbarHostState)
        }
    }
}