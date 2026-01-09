package com.testdeymervilla.interdata.features.locality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.map
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.usecase.locality.FetchLocalityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class LocalityUiState {
    data object Loading: LocalityUiState()
    data object Success: LocalityUiState()
    data class Error(val message: String? = null): LocalityUiState()
}

@HiltViewModel
class LocalityScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchLocalityUseCase: FetchLocalityUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<LocalityUiState>(LocalityUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _localityDomain = MutableStateFlow<LocalityDomain?>(null)
    val localityDomain: StateFlow<LocalityDomain?> = _localityDomain.asStateFlow()

    fun getLocality(localityId: Int) {
        fetchLocalityUseCase(localityId).map { locality ->
            _localityDomain.value = locality
        }.success {
            _uiState.emit(LocalityUiState.Success)
        }.failure { exception ->
            exception.default {
                _uiState.emit(LocalityUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }
}