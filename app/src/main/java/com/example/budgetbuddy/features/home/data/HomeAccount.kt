package com.example.budgetbuddy.features.home.data

import com.example.core.data.bank.Bank

data class HomeAccount(
    val id: Long,
    val name: String,
    val bank: Bank,
    val balance: String,
)
