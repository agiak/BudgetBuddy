package com.example.features.statics.data.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.bank.Bank
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.features.statics.R
import com.example.features.statics.impl.data.data.CommonStatCategory
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.data.data.StaticsUiState
import com.example.features.statics.impl.data.data.mergeCommonStats
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
            repository.getStatsObservable().collect { data ->
                if (data.accounts.isNotEmpty()) fetchData(data.transactions.size > 2)
                else _state.value = StaticsUiState.Result(listOf(StaticsItem.EmptyStats))
            }
        }
    }

    private suspend fun fetchData(isTransactionsAvailable: Boolean) {
        viewModelScope.launch {
            emitLoading()
            runCatching {
                createStats(isTransactionsAvailable)
            }.onSuccess { items ->
                _state.value = StaticsUiState.Result(items)
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun emitLoading() {
        _state.value = StaticsUiState.Loading
    }

    private suspend fun createStats(isTransactionsAvailable: Boolean): List<StaticsItem> =
        withContext(dispatchers.backgroundThread()) {
            val accountsStats = accountStats()
            val transactionsStats = if (isTransactionsAvailable) transactionsStats() else listOf()

            mergeCommonStats(accountsStats, transactionsStats)
        }

    private suspend fun transactionsStats(): List<StaticsItem> =
        withContext(dispatchers.backgroundThread()) {
            val largerTransactionsDeferred = async { repository.fetchMostLargerTransactions() }
            val mostUsedAccountsDeferred = async { repository.fetchMostUsedAccounts() }
            val investmentProgressDeferred = async { repository.fetchInvestmentProgress() }

            val result = awaitAll(largerTransactionsDeferred, mostUsedAccountsDeferred, investmentProgressDeferred)

            val commonStats = listOf(
                CommonStatCategory(
                    title = R.string.statics_common_stat_category_transactions,
                    results = (result[0] as List<TransactionDB>).toLargerTransactions()
                ),
                CommonStatCategory(
                    title = R.string.statics_common_stat_category_accounts_with_most_transactions,
                    results = (result[1] as Map<AccountDB, Int>).toMostUsedAccounts()
                ),
            )

            val commonStatsItem = StaticsItem.CommonStats(commonStats)
            val investmentProgressItem =
                StaticsItem.InvestmentProgress(result[2] as Map<String, Double>)

            listOf(commonStatsItem, investmentProgressItem)
        }

    private suspend fun accountStats(): List<StaticsItem> =
        withContext(dispatchers.backgroundThread()) {
            val mostValuableAccountsDeferred = async { repository.fetchMostValuableAccounts() }
            val mostTrustedBanks = async { repository.fetchMostTrustedBanks() }

            val result = awaitAll(
                mostValuableAccountsDeferred,
                mostTrustedBanks,
            )

            val commonStats = listOf(
                CommonStatCategory(
                    title = R.string.statics_common_stat_category_accounts,
                    results = (result[0] as List<AccountDB>).toMostValuableAccounts()
                ),
                CommonStatCategory(
                    title = R.string.statics_common_stat_category_trusted_banks,
                    results = (result[1] as Map<Bank, Double>).toMostTrustedBanks()
                ),
            )

            val commonStatsItem = StaticsItem.CommonStats(commonStats)

            listOf(commonStatsItem)
        }
}
