package com.example.mywallet.features.accountModule.accounts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.core.data.common.AppValues
import com.example.mywallet.features.accountModule.accounts.data.Account
import com.example.mywallet.features.accountModule.accounts.data.toAccounts
import com.example.mywallet.features.accountModule.accounts.domain.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val repository: AccountsRepository,
) : ViewModel() {

    val accounts: StateFlow<List<Account>> = repository.fetchAccounts()
        .map { storedAccounts ->
            AppValues.accounts = storedAccounts
            storedAccounts.toAccounts()
                .groupBy { it.bank.name }
                .flatMap { it.value }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
}