package com.example.quicklogin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.quicklogin.impl.data.QuickLoginState
import com.example.features.quicklogin.impl.domain.QuickLoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuickLoginViewModel @Inject constructor(
    private val repository: QuickLoginRepository,
): ViewModel() {

    private val _state = MutableStateFlow<QuickLoginState>(
        QuickLoginState.Loading)
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