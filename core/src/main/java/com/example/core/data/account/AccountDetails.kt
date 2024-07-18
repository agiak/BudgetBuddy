package com.example.core.data.account

import com.example.core.data.bank.Bank

data class AccountDetails(
    val id: Long,
    val name: String,
    val bank: Bank,
    val date: String,
    val balance: String,
)