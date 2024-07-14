package com.example.mywallet.features.account.accounts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.core.data.common.AppValues
import com.example.mywallet.features.account.accounts.data.Account
import com.example.mywallet.features.account.accounts.data.toAccounts
import com.example.mywallet.features.account.accounts.domain.AccountsRepository
import com.example.mywallet.features.account.accountsFilter.data.AccountFilterSelection
import com.example.mywallet.features.account.accountsFilter.data.AccountsFilterGroupBy
import com.example.mywallet.features.account.accountsFilter.data.AccountsFilterOrderBy
import com.example.mywallet.features.account.accountsFilter.data.AccountsFiltersData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val repository: AccountsRepository,
) : ViewModel() {

    private var selectedFilters = AccountFilterSelection(groupBy = AccountsFilterGroupBy.BY_BANK)

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    init {
        viewModelScope.launch {
            repository.fetchAccounts().collectLatest { storedAccounts ->
                AppValues.accounts = storedAccounts
                _accounts.update {
                    storedAccounts.toAccounts()
                        .groupBy { it.bank.name }
                        .flatMap { it.value }
                }
            }
        }
    }

    fun applyFilters(filterSelection: AccountFilterSelection) {
        viewModelScope.launch {
            runCatching {
                selectedFilters = filterSelection
                repository.applyFilters(filterSelection)
            }.onSuccess { filteredAccounts ->
                _accounts.update { filteredAccounts.toAccounts() }
            }
        }
    }

    fun fetchSelectedFiltersData(): AccountsFiltersData {
        val groupByOptions = AccountsFilterGroupBy.entries.map {
            it.apply { isSelected = (this == selectedFilters.groupBy) }
        }

        val orderByOptions = AccountsFilterOrderBy.entries.map {
            it.apply { isSelected = (this == selectedFilters.orderBy) }
        }

        return AccountsFiltersData(groupByOptions, orderByOptions)
    }
}