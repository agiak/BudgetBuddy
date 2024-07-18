package com.example.mywallet.features.account.accountAdd.data

import com.example.core.data.bank.Bank

data class AccountNew(
    val name: String,
    val bank: Bank,
    val balance: Double,
    val date: String,
)