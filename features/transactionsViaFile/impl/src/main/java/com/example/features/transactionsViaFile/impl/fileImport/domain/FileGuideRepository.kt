package com.example.features.transactionsViaFile.impl.fileImport.domain

import com.example.core.storage.data.TransactionDB
import java.io.File

interface FileGuideRepository {

    suspend fun createTransactions(file: File): List<TransactionDB>
}