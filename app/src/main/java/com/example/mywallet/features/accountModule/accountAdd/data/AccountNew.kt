package com.example.mywallet.features.accountModule.accountAdd.data

import com.example.mywallet.core.data.bank.Bank

data class AccountNew(
    val name: String,
    val bank: Bank,
    val balance: Double,
    val date: String,
)