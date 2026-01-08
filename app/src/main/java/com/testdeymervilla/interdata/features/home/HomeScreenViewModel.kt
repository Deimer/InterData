package com.testdeymervilla.interdata.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testdeymervilla.interdata.di.IoDispatcher
import com.testdeymervilla.presentation.utils.default
import com.testdeymervilla.presentation.utils.failure
import com.testdeymervilla.presentation.utils.launchIn
import com.testdeymervilla.presentation.utils.success
import com.testdeymervilla.repository.domain.UserDomain
import com.testdeymervilla.usecase.user.FetchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Success(val user: UserDomain): HomeUiState()
    data class Error(val message: String? = null): HomeUiState()
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchUserUseCase: FetchUserUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init { launchGetUser() }

    private fun launchGetUser() {
        fetchUserUseCase().success { user ->
            _uiState.emit(HomeUiState.Success(user))
        }.failure { exception ->
            exception.default {
                _uiState.emit(HomeUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }
}