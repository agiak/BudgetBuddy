package com.example.mywallet.features.transactionsModule.fileTransactions.domain

import com.example.core.storage.data.TransactionDB
import java.io.File

interface FileGuideRepository {

    suspend fun createTransactions(file: File): List<TransactionDB>
}