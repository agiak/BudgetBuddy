package com.example.features.statics.data.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.bank.Bank
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.features.statics.impl.data.data.CommonStatCategory
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.data.data.StaticsUiState
import com.example.features.statics.impl.data.data.toLargerTransactions
import com.example.features.statics.impl.data.data.toMostTrustedBanks
import com.example.features.statics.impl.data.data.toMostUsedAccounts
import com.example.features.statics.impl.data.data.toMostValuableAccounts
import com.example.features.statics.impl.data.domain.StaticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StaticsViewModel @Inject constructor(
    private val repository: StaticsRepository,
    private val dispatchers: IDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow<StaticsUiState>(StaticsUiState.Idle)
    val state: StateFlow<StaticsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            emitLoading()
            repository.init()
            fetchData()
        }
    }

    private suspend fun fetchData() {
        viewModelScope.launch {
            runCatching {
                createCommonStats()
            }.onSuccess { items ->
                _state.value = StaticsUiState.Result(listOf(items))
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun emitLoading() {
        _state.value = StaticsUiState.Loading
    }

    private suspend fun createCommonStats(): StaticsItem.CommonStats =
        withContext(dispatchers.backgroundThread()) {

            val mostValuableAccountsDeferred = async { repository.fetchMostValuableAccounts() }
            val mostLargerTransactionsDeferred = async { repository.fetchMostLargerTransactions() }
            val mostUsedAccountsDeferred = async { repository.fetchMostUsedAccounts() }
            val mostTrustedBanks = async { repository.fetchMostTrustedBanks() }

            val result = awaitAll(
                mostValuableAccountsDeferred,
                mostLargerTransactionsDeferred,
                mostUsedAccountsDeferred,
                mostTrustedBanks
            )

            val commonStats = listOf(
                CommonStatCategory(
                    title = "Most valuable accounts",
                    results = (result[0] as List<AccountDB>).toMostValuableAccounts()
                ),
                CommonStatCategory(
                    title = "Larger transactions",
                    results = (result[1] as List<TransactionDB>).toLargerTransactions()
                ),
                CommonStatCategory(
                    title = "Accounts with most transactions",
                    results = (result[2] as Map<AccountDB, Int>).toMostUsedAccounts()
                ),
                CommonStatCategory(
                    title = "Most trusted banks",
                    results = (result[3] as Map<Bank, Double>).toMostTrustedBanks()
                ),
            )

            StaticsItem.CommonStats(commonStats)
        }
}
