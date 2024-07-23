package com.example.features.accountAdd.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.accountAdd.impl.data.AccountNew
import com.example.features.accountAdd.impl.data.toAccountDB
import com.example.features.accountAdd.impl.domain.AddAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val repository: AddAccountRepository,
): ViewModel() {

    private val _state = MutableStateFlow<AddAccountUiState>(AddAccountUiState.Idle)
    val state: StateFlow<AddAccountUiState> = _state.asStateFlow()

    fun createAccount(account: AccountNew) {
        viewModelScope.launch {
            _state.value = AddAccountUiState.Loading
            runCatching {
                repository.addAccount(accountDB = account.toAccountDB())
            }.onSuccess {
                _state.value = AddAccountUiState.Success
            }.onFailure {
                Timber.e(it)
                _state.value = AddAccountUiState.Error(it.message.toString())
            }
        }
    }
}

sealed class AddAccountUiState {
    data object Idle: AddAccountUiState()
    data object Loading: AddAccountUiState()
    data object Success: AddAccountUiState()
    data class Error(val message: String): AddAccountUiState()
}