package com.testdeymervilla.interdata.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.BuildConfig
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.compareVersion
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.isIOEx
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.map
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.usecase.user.FetchVersionUseCase
import com.testdeymervilla.usecase.user.InSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class SplashUiState {
    data object Default: SplashUiState()
    data object ConnectionError: SplashUiState()
    data class Error(val message: String? = null): SplashUiState()
}

sealed class VersionValidation {
    data class SameVersion(val localVersion: String): VersionValidation()
    data object GreaterVersion: VersionValidation()
    data object LowerVersion: VersionValidation()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val inSessionUseCase: InSessionUseCase,
    private val fetchVersionUseCase: FetchVersionUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Default)
    val uiState = _uiState.asStateFlow()

    private val _versionValidation = MutableStateFlow<VersionValidation?>(null)
    val versionValidation = _versionValidation.asStateFlow()

    private val _userIsLogged = MutableStateFlow<Boolean?>(null)
    val userIsLogged = _userIsLogged.asStateFlow()

    init {
        fetchVersion()
    }

    private fun fetchVersion() {
        fetchVersionUseCase().map { serviceVersion ->
            val localVersion = BuildConfig.VERSION_NAME
            val result = localVersion.compareVersion(serviceVersion)
            val validation = when {
                result > 0 -> VersionValidation.GreaterVersion
                result < 0 -> VersionValidation.LowerVersion
                else -> VersionValidation.SameVersion(localVersion)
            }
            _versionValidation.emit(validation)
        }.success { version ->
            inSession()
        }.failure { exception ->
            exception.isIOEx {
                _uiState.emit(SplashUiState.ConnectionError)
            }
            exception.default {
                _uiState.emit(SplashUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }

    private fun inSession() {
        inSessionUseCase().success { inSession ->
            _userIsLogged.value = inSession
        }.failure { exception ->
            exception.isIOEx {
                _uiState.emit(SplashUiState.ConnectionError)
            }
            exception.default {
                _uiState.emit(SplashUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }
}