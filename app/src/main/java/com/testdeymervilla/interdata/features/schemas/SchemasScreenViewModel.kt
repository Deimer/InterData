package com.testdeymervilla.interdata.features.schemas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.isIOEx
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.map
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.usecase.schema.FetchSchemasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

sealed class SchemasUiState {
    data object Loading: SchemasUiState()
    data object Success: SchemasUiState()
    data object ConnectionError: SchemasUiState()
    data class Error(val message: String? = null): SchemasUiState()
}

@HiltViewModel
class SchemasScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchSchemasUseCase: FetchSchemasUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<SchemasUiState>(SchemasUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isRefreshingSchemas = MutableStateFlow(false)
    val isRefreshingSchemas: StateFlow<Boolean> = _isRefreshingSchemas.asStateFlow()

    private val _schemaList = MutableStateFlow<List<SchemaDomain>>(emptyList())
    val schemaList: StateFlow<List<SchemaDomain>> = _schemaList.asStateFlow()

    init { getSchemas() }

    fun getSchemas(isRefresh: Boolean = false) {
        fetchSchemasUseCase().onStart {
            _isRefreshingSchemas.value = isRefresh
        }.map { schemas ->
            _schemaList.value = schemas
        }.success {
            _uiState.emit(SchemasUiState.Success)
        }.failure { exception ->
            exception.isIOEx {
                _uiState.emit(SchemasUiState.ConnectionError)
            }
            exception.default {
                _uiState.emit(SchemasUiState.Error(exception.message.orEmpty()))
            }
        }.onCompletion {
            _isRefreshingSchemas.value = false
        }.launchIn(viewModelScope, ioDispatcher)
    }
}