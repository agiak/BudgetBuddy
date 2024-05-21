package com.example.mywallet.features.home.data

sealed class HomeItem {

    data class Summary(
        val totalBalance: String,
        val lastChange: String
    ): HomeItem()

    data class Activity(val list: List<HomeTransaction>, val transactionsCost: String): HomeItem()

    data class Accounts(val accounts: List<HomeAccount>): HomeItem()

    data object TransferFunds: HomeItem()

    data object AddAccount: HomeItem()
}
