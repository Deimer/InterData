package com.testdeymervilla.interdata.features.schema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.map
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.usecase.schema.FetchSchemaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class SchemaUiState {
    data object Loading: SchemaUiState()
    data object Success: SchemaUiState()
    data class Error(val message: String? = null): SchemaUiState()
}

@HiltViewModel
class SchemaScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchSchemaUseCase: FetchSchemaUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<SchemaUiState>(SchemaUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _schemaDomain = MutableStateFlow<SchemaDomain?>(null)
    val schemaDomain: StateFlow<SchemaDomain?> = _schemaDomain.asStateFlow()

    fun getSchema(schemaId: Int) {
        fetchSchemaUseCase(schemaId).map { schema ->
            _schemaDomain.value = schema
        }.success {
            _uiState.emit(SchemaUiState.Success)
        }.failure { exception ->
            exception.default {
                _uiState.emit(SchemaUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }
}