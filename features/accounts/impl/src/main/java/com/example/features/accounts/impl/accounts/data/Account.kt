package com.example.features.accounts.impl.accounts.data

import com.example.core.data.bank.Bank

data class Account(
    val id: Long,
    val name: String,
    val bank: Bank,
    val balance: String,
    val logoID: Int = 0,
)