package com.example.mywallet.features.home.data

import com.example.mywallet.core.data.bank.Bank

data class HomeAccount(
    val id: Long,
    val name: String,
    val bank: Bank,
    val balance: String,
    val date: String,
)
