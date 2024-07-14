package com.example.mywallet.features.transactionsModule.transactionsSelection.data

data class TransactionSelection(
    val details: String,
    val amount: String,
    val date: String,
    val description: String,
    var isSelected: Boolean,
)
