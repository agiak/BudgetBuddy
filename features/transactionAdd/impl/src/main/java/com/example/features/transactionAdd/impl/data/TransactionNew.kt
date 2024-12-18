package com.example.features.transactionAdd.impl.data

import com.example.core.data.common.TransactionType

data class TransactionNew(
    val date: String,
    val amount: Double,
    val type: TransactionType,
    val accountFrom: Long,
    val accountFromName: String,
    val bankFromIcon: Int,
    val accountTo: Long?,
    val accountToName: String?,
    val bankToIcon: Int?,
    val description: String,
    val applyTransaction: Boolean,
)
