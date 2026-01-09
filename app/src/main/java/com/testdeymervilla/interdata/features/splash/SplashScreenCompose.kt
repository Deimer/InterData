package com.testdeymervilla.interdata.features.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.alerts.ConnectionErrorScreenCompose
import com.testdeymervilla.presentation.alerts.ErrorDetailCompose
import com.testdeymervilla.presentation.components.LottieCompose
import com.testdeymervilla.presentation.components.StatusCompose
import com.testdeymervilla.presentation.models.VersionStatus
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.utils.PresentationConstants.AnimationConstants.DURATION_LAUNCH_EFFECT
import com.testdeymervilla.presentation.utils.PresentationConstants.AnimationConstants.ITERATION_SIMPLE
import kotlinx.coroutines.delay

@Composable
fun SplashScreenCompose(
    viewModel: SplashViewModel = hiltViewModel(),
    attributes: SplashScreenAttributes
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val versionValidation by viewModel.versionValidation.collectAsState()
    val userIsLogged by viewModel.userIsLogged.collectAsState()

    val navigate: () -> Unit = when(userIsLogged) {
        true -> attributes.actions.onPrimaryAction
        false -> attributes.actions.onSecondaryAction
        else -> ({})
    }

    when(uiState) {
        is SplashUiState.Default -> BodyContent(
            versionValidation = versionValidation,
            launchGetVersion = viewModel::fetchVersion,
            navigate = navigate
        )
        is SplashUiState.ConnectionError -> ConnectionErrorScreenCompose()
        is SplashUiState.Error -> {
            val errorMessage = (uiState as SplashUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyContent(
    versionValidation: VersionValidation? = null,
    launchGetVersion: () -> Unit = {},
    navigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(R.dimen.dimen_12)
            ),
        contentAlignment = Alignment.Center
    ) {
        LottieCompose(
            rawRes = R.raw.ic_welcome,
            size = dimensionResource(id = R.dimen.dimen_220),
            iterations = ITERATION_SIMPLE,
            onAnimationEnd = { launchGetVersion.invoke() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.dimen_12)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.title_welcome),
                style = MaterialTheme.typography.headlineLarge.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = stringResource(R.string.legend_welcome),
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.dimen_8))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dimen_16))
                    .fillMaxWidth()
            )
            when(versionValidation) {
                is VersionValidation.SameVersion -> {
                    StatusCompose(
                        versionStatus = VersionStatus.SAME,
                        versionName = versionValidation.localVersion,
                    )
                    val currentNavigate by rememberUpdatedState(navigate)
                    LaunchedEffect(versionValidation) {
                        delay(DURATION_LAUNCH_EFFECT)
                        currentNavigate()
                    }
                }
                is VersionValidation.GreaterVersion -> {
                    StatusCompose(
                        versionStatus = VersionStatus.GREATER,
                        onClickButton = navigate
                    )
                }
                is VersionValidation.LowerVersion -> {
                    StatusCompose(
                        versionStatus = VersionStatus.LOWER,
                        onClickButton = navigate
                    )
                }
                else -> {}
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    InterDataTheme {
        Scaffold {
            BodyContent(
                versionValidation = VersionValidation.LowerVersion,
                navigate = {}
            )
        }
    }
}