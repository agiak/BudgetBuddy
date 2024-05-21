package com.example.mywallet.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.core.data.common.AppValues
import com.example.mywallet.core.data.common.toCurrencyBalance
import com.example.mywallet.features.home.data.HomeItem
import com.example.mywallet.features.home.data.toHomeAccountList
import com.example.mywallet.features.home.data.toHomeTransactions
import com.example.mywallet.features.home.domain.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    val homeState: StateFlow<List<HomeItem>> = combine(
        repository.fetchAccounts(),
        repository.fetchLastTransactions(),
        repository.fetchTotalBalance(),
    ) { accounts, transactions, totalBalance ->

        ArrayList<HomeItem>().apply {
            val summaryItem = HomeItem.Summary(
                totalBalance = totalBalance.toCurrencyBalance(),
                lastChange = "+300$"
            )
            add(summaryItem)

            add(HomeItem.TransferFunds)

            if (transactions.isNotEmpty()) {
                val activityItem = HomeItem.Activity(
                    list = transactions.toHomeTransactions(),
                    transactionsCost = (transactions.sumOf { it.amount }).toCurrencyBalance()
                )
                AppValues.hasTransactions = true
                add(activityItem)
            }

            if (accounts.isNotEmpty()) {
                val accountsItem = HomeItem.Accounts(
                    accounts = accounts.toHomeAccountList()
                        .groupBy { it.bank }
                        .flatMap { it.value }
                )
                add(accountsItem)
                AppValues.accounts = accounts
            }

            add(HomeItem.AddAccount)
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
}
