package com.example.mywallet.features.account.accountDetails.data

sealed class AccountDetailsItem {
    data class Statics(val staticsInfo: AccountDetailsStatics): AccountDetailsItem()
    data class Activity(val recentTransactions: List<AccountTransaction>): AccountDetailsItem()
}



data class AccountDetailsStatics(
    val income: String,
    val outcome: String,
    val numOfTransactions: String,
    val lastMonthChange: String,
    val lastMonthChangePercentage: Double,
)

data class AccountStatics(
    val income: Double,
    val outcome: Double,
    val numOfTransactions: Int,
    val lastMonthChange: Double,
    val lastMonthChangePercentage: Double,
)

data class AccountTransaction(
    val id: Long,
    val amount: String,
    val date: String,
    val details: String,
    val description: String,
)
