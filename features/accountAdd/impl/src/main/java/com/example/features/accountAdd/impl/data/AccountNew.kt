package com.example.features.accountAdd.impl.data

import com.example.core.data.bank.Bank

data class AccountNew(
    val name: String,
    val bank: Bank,
    val balance: Double,
)