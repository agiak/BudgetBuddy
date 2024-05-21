package com.example.mywallet.features.accountModule.accounts.data

import com.example.mywallet.core.data.bank.Bank

data class Account(
    val id: Long,
    val name: String,
    val bank: Bank,
    val balance: String,
    val logoID: Int = 0,
)