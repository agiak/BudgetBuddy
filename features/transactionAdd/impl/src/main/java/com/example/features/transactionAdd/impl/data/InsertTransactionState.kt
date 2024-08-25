package com.example.features.transactionAdd.impl.data

sealed class InsertTransactionState {
    data object Success : InsertTransactionState()
    data object InsufficientFunds : InsertTransactionState()
    data object AccountNotFound : InsertTransactionState()
}