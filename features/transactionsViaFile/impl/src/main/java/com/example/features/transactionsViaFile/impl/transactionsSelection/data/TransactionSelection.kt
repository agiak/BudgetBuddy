package com.example.features.transactionsViaFile.impl.transactionsSelection.data

import com.example.core.data.common.TransactionType

data class TransactionSelection(
    val details: String,
    val amount: String,
    val date: String,
    val description: String,
    var isSelected: Boolean,
    val type: TransactionType,
)
