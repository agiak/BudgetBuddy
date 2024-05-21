package com.example.mywallet.features.activation.quicklogin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.features.accountModule.account.data.AccountTransaction
import com.example.mywallet.features.accountModule.account.data.toAccountTransactions
import com.example.mywallet.features.accountModule.account.domain.AccountRepository
import com.example.mywallet.features.activation.quicklogin.data.QuickLoginState
import com.example.mywallet.features.activation.quicklogin.data.UserState
import com.example.mywallet.features.activation.quicklogin.domain.QuickLoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuickLoginViewModel @Inject constructor(
    private val repository: QuickLoginRepository,
): ViewModel() {

    private val _state = MutableStateFlow<QuickLoginState>(QuickLoginState.Loading)
    val state: StateFlow<QuickLoginState> = _state

    init {
        fetchUserState()
    }

    private fun fetchUserState() {
        viewModelScope.launch {
            runCatching {
                repository.getUserState()
            }.onSuccess {
                _state.value = QuickLoginState.Result(userState = it)
            }.onFailure {
                Timber.e(it)
                _state.value = QuickLoginState.Error(errorMessage = it.message.toString())
            }
        }
    }

}