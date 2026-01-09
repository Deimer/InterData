package com.testdeymervilla.interdata.features.localities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.isIOEx
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.map
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.usecase.locality.FetchLocalitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

sealed class LocalitiesUiState {
    data object Loading: LocalitiesUiState()
    data object Success: LocalitiesUiState()
    data object ConnectionError: LocalitiesUiState()
    data class Error(val message: String? = null): LocalitiesUiState()
}

@HiltViewModel
class LocalitiesScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchLocalitiesUseCase: FetchLocalitiesUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<LocalitiesUiState>(LocalitiesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isRefreshingLocalities = MutableStateFlow(false)
    val isRefreshingLocalities: StateFlow<Boolean> = _isRefreshingLocalities.asStateFlow()

    private val _localityList = MutableStateFlow<List<LocalityDomain>>(emptyList())
    val localityList: StateFlow<List<LocalityDomain>> = _localityList.asStateFlow()

    init { getLocalities() }

    fun getLocalities(isRefresh: Boolean = false) {
        fetchLocalitiesUseCase().onStart {
            _isRefreshingLocalities.value = isRefresh
        }.map { schemas ->
            _localityList.value = schemas
        }.success {
            _uiState.emit(LocalitiesUiState.Success)
        }.failure { exception ->
            exception.isIOEx {
                _uiState.emit(LocalitiesUiState.ConnectionError)
            }
            exception.default {
                _uiState.emit(LocalitiesUiState.Error(exception.message.orEmpty()))
            }
        }.onCompletion {
            _isRefreshingLocalities.value = false
        }.launchIn(viewModelScope, ioDispatcher)
    }
}