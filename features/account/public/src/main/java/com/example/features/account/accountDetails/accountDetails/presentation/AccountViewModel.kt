package com.example.features.account.accountDetails.accountDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.APP_DATE_FORMAT
import com.example.core.data.account.AccountDetails
import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountDetailsItem
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountDetailsStatics
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountStatics
import com.example.features.account.accountDetails.impl.accountDetails.data.toAccountDetails
import com.example.features.account.accountDetails.impl.accountDetails.data.toAccountTransactions
import com.example.features.account.accountDetails.impl.domain.AccountRepository
import com.example.features.account.accountDetails.impl.editAccount.data.AccountEditableInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: AccountRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val state: StateFlow<AccountUiState> = _state.asStateFlow()

    var accountID: Long = 0L

    val account: StateFlow<AccountDetails?> by lazy {
        repository.getAccountObservable(accountID = accountID).map { accountDB: AccountDB? ->
            accountDB?.toAccountDetails()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
    }

    fun loadState() {
        viewModelScope.launch {
            runCatching {
                val accountDeferred = async { repository.fetchAccountDetails(accountID) }
                val transactionsDeferred = async { repository.fetchTransactions(accountID) }
                val staticsDeferred = async { repository.fetchAccountStatics(accountID) }

                awaitAll(accountDeferred, transactionsDeferred, staticsDeferred)
            }.onSuccess { accountItems ->
                val accountData: AccountDetails? =
                    (accountItems[0] as? AccountDB?)?.toAccountDetails()
                val transactions: List<TransactionDB> = accountItems[1] as List<TransactionDB>
                val statics: AccountStatics? = accountItems[2] as? AccountStatics?

                val uiItems = ArrayList<AccountDetailsItem>().apply {
                    constructStaticsItem(statics)?.let { add(it) }
                    constructActivityItem(transactions)?.let { add(it) }
                }
                _state.value = AccountUiState.Result(uiItems, accountData)
            }.onFailure {

            }
        }
    }

    private fun constructStaticsItem(statics: AccountStatics?): AccountDetailsItem.Statics? =
        statics?.let {
            AccountDetailsItem.Statics(
                AccountDetailsStatics(
                    income = it.income.toCurrencyBalance(),
                    outcome = if (it.outcome == 0.0) "0" else ((-1) * it.outcome).toCurrencyBalance(),
                    numOfTransactions = it.numOfTransactions.toString(),
                    lastMonthChange = it.lastMonthChange.toString(),
                    lastMonthChangePercentage = it.lastMonthChangePercentage
                )
            )
        }

    private fun constructActivityItem(transactions: List<TransactionDB>): AccountDetailsItem.Activity? =
        if (transactions.isNotEmpty()) {
            val format = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
            val transactions = transactions.toAccountTransactions().sortedByDescending {
                format.parse(it.date)
            }
            AccountDetailsItem.Activity(transactions)
        } else null

    fun updateAccountDetails(newAccountInfo: AccountEditableInfo) {
        account.value?.let {
            viewModelScope.launch {
                runCatching {
                    val updatedAccount = it.copy(
                        name = newAccountInfo.name,
                        bank = newAccountInfo.bank,
                        balance = newAccountInfo.balance
                    )
                    repository.updateAccount(updatedAccount)
                }.onSuccess {
                    _state.value = AccountUiState.AccountSaved
                }
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            emitLoading()
            runCatching {
                repository.deleteAccount(accountID)
            }.onSuccess {
                _state.value = AccountUiState.AccountDeleted
            }.onFailure {
                _state.value = AccountUiState.Error(it.message.toString())
            }
        }
    }

    private fun emitLoading() {
        _state.value = AccountUiState.Loading
    }
}

sealed class AccountUiState {
    data object Loading : AccountUiState()
    data class Result(
        val accountItems: List<AccountDetailsItem>,
        val generalInformation: AccountDetails?
    ) : AccountUiState()
    data object AccountDeleted : AccountUiState()
    data object AccountSaved : AccountUiState()
    data class Error(val errorMessage: String): AccountUiState()
}
