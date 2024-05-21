package com.example.mywallet.features.transactionsModule.transactions.data

import com.example.mywallet.core.data.common.TransactionType

data class Transaction(
    val id: Long,
    val type: TransactionType,
    val amount: String,
    val details: String,
    val date: String,
    val bankFromIcon: Int,
    val bankToIcon: Int? = null,
    val description: String,
)
