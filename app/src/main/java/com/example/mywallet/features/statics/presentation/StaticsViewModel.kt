package com.example.mywallet.features.statics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.statics.data.CommonStatCategory
import com.example.mywallet.features.statics.data.StaticsItem
import com.example.mywallet.features.statics.data.StaticsUiState
import com.example.mywallet.features.statics.data.toLargerTransactions
import com.example.mywallet.features.statics.data.toMostUsedAccounts
import com.example.mywallet.features.statics.data.toMostValuableAccounts
import com.example.mywallet.features.statics.domain.StaticsRepository
import com.example.mywallet.features.statics.domain.StaticsRepositoryImpl
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB
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

            val newRepo = (repository as StaticsRepositoryImpl)
            val mostValuableAccountsDeferred = async { newRepo.fetchMostValuableAccounts() }
            val mostLargerTransactionsDeferred = async { newRepo.fetchMostLargerTransactions() }
            val mostUsedAccountsDeferred = async { newRepo.fetchMostUsedAccounts() }

            val result = awaitAll(
                mostValuableAccountsDeferred,
                mostLargerTransactionsDeferred,
                mostUsedAccountsDeferred,
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
            )

            StaticsItem.CommonStats(commonStats)
        }

}