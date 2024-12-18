package com.example.features.transactions.impl.transactions.data

import com.example.core.data.common.TransactionType

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
