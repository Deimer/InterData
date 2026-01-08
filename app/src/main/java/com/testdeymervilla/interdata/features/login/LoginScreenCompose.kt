package com.testdeymervilla.interdata.features.login

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.alerts.ConnectionErrorScreenCompose
import com.testdeymervilla.presentation.alerts.ErrorDetailCompose
import com.testdeymervilla.presentation.alerts.LoadingScreenCompose
import com.testdeymervilla.presentation.components.ButtonSize
import com.testdeymervilla.presentation.components.ButtonStyle
import com.testdeymervilla.presentation.components.PasswordEditTextCompose
import com.testdeymervilla.presentation.components.TapButtonCompose
import com.testdeymervilla.presentation.components.TopBarCompose
import com.testdeymervilla.presentation.components.UsernameEditTextCompose
import com.testdeymervilla.presentation.theme.InterDataTheme

@Composable
fun LoginScreenCompose(
    viewModel: LoginScreenViewModel = hiltViewModel(),
    attributes: LoginScreenAttributes
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val formState by viewModel.formState.collectAsState()

    when(uiState) {
        is LoginUiState.Success -> attributes.actions.onPrimaryAction.invoke()
        is LoginUiState.ConnectionError -> ConnectionErrorScreenCompose()
        is LoginUiState.Loading -> LoadingScreenCompose()
        is LoginUiState.Default -> BodyContent(
            formState = formState,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginClick = viewModel::launchLogin
        )
    }

    val errorMessage = when(errorState) {
        is LoginErrorState.Error -> (errorState as LoginErrorState.Error).message
        LoginErrorState.FormError -> stringResource(R.string.enter_username_and_password)
        LoginErrorState.UsernameError -> stringResource(R.string.enter_username)
        LoginErrorState.PasswordError -> stringResource(R.string.enter_password)
        LoginErrorState.CredentialsError -> stringResource(R.string.invalid_credentials)
        LoginErrorState.None -> null
    }

    errorMessage?.let {
        ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
    }
}

@Composable
private fun BodyContent(
    formState: LoginFormState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    val email = formState.username
    val password = formState.password
    Column(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = dimensionResource(R.dimen.dimen_12)
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBarCompose(
            title = stringResource(id = R.string.log_in),
            modifier = Modifier,
        )
        UsernameEditTextCompose(
            label = stringResource(id = R.string.username),
            value = email,
            onValueChange = onUsernameChange,
            placeholder = stringResource(id = R.string.username),
            imeAction = ImeAction.Next,
        )
        PasswordEditTextCompose(
            label = stringResource(id = R.string.password),
            value = password,
            onValueChange = onPasswordChange,
            placeholder = stringResource(id = R.string.password),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.dimen_16))
        )
        TapButtonCompose(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.dimen_46)),
            text = stringResource(id = R.string.log_in),
            buttonStyle = ButtonStyle.Primary,
            size = ButtonSize.Small,
            onClick = { onLoginClick() }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BodyContentPreview() {
    InterDataTheme {
        Scaffold {
            BodyContent(
                formState = LoginFormState(
                    username = "testUsername",
                    password = "password123"
                ),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }
    }
}