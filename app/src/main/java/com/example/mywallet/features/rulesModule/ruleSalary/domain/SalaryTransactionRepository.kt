package com.example.mywallet.features.rulesModule.ruleSalary.domain

import com.example.core.storage.data.TransactionDB

interface SalaryTransactionRepository {

    suspend fun addTransaction(transactionDB: TransactionDB, applyTransaction: Boolean)
}