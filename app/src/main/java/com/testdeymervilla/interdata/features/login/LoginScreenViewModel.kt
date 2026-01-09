package com.testdeymervilla.interdata.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.cleanInputs
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.ensure
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.isIOEx
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.start
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

sealed class LoginUiState {
    data object Default: LoginUiState()
    data object Loading: LoginUiState()
    data object Success: LoginUiState()
    data object ConnectionError: LoginUiState()
}

sealed class LoginErrorState {
    data object None: LoginErrorState()
    data object FormError: LoginErrorState()
    data object UsernameError: LoginErrorState()
    data object PasswordError: LoginErrorState()
    data object CredentialsError: LoginErrorState()
    data class Error(val message: String? = null): LoginErrorState()
}

data class LoginFormState(
    val username: String = "",
    val password: String = "",
)

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loginUseCase: LoginUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Default)
    val uiState = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<LoginErrorState>(LoginErrorState.None)
    val errorState = _errorState.asStateFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> = _formState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _formState.value = _formState.value.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _formState.value = _formState.value.copy(password = newPassword)
    }

    fun launchLogin() {
        val formState = _formState.value
        val username = formState.username.trim().cleanInputs()
        val password = formState.password.trim().cleanInputs()
        loginUseCase(username, password).ensure(
            condition = { username.isNotBlank() && password.isNotBlank() },
            onFailure = { handleInvalidForm(LoginErrorState.FormError) }
        ).ensure(
            condition = { username.isNotBlank() },
            onFailure = { handleInvalidForm(LoginErrorState.UsernameError) }
        ).ensure(
            condition = { password.isNotBlank() },
            onFailure = { handleInvalidForm(LoginErrorState.PasswordError) }
        ).start {
            _errorState.emit(LoginErrorState.None)
            _uiState.emit(LoginUiState.Loading)
        }.success { loginSuccess ->
            if(loginSuccess) {
                _uiState.emit(LoginUiState.Success)
            } else {
                _uiState.emit(LoginUiState.Default)
                _errorState.emit(LoginErrorState.CredentialsError)
            }
        }.failure { exception ->
            exception.isIOEx {
                _uiState.emit(LoginUiState.ConnectionError)
            }
            exception.default {
                _errorState.emit(LoginErrorState.Error(exception.message.orEmpty()))
            }
        }.onCompletion {
            _uiState.emit(LoginUiState.Default)
        }.launchIn(viewModelScope, ioDispatcher)
    }

    private suspend fun handleInvalidForm(error: LoginErrorState) {
        _uiState.emit(LoginUiState.Default)
        _errorState.emit(error)
    }
}