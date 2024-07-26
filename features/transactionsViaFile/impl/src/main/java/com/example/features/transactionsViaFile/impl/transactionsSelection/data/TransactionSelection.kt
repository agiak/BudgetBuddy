package com.example.features.transactionsViaFile.impl.transactionsSelection.data

data class TransactionSelection(
    val details: String,
    val amount: String,
    val date: String,
    val description: String,
    var isSelected: Boolean,
)
