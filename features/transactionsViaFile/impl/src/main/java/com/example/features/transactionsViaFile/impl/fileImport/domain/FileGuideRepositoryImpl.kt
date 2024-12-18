package com.example.features.transactionsViaFile.impl.fileImport.domain

import com.example.common.myutils.formatToDateString
import com.example.core.data.common.AppValues
import com.example.core.data.common.TransactionType
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.domain.ext.toCurrencyDouble
import com.example.core.storage.data.TransactionDB
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class FileGuideRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
) : FileGuideRepository {

    override suspend fun createTransactions(file: File): List<TransactionDB> =
        withContext(dispatchers.backgroundThread()) {
            createList(file)
        }

    private operator fun <T> List<T>.component6(): T = get(5)

    private fun createList(file: File): List<TransactionDB> {
        val reader = file.bufferedReader()
        //   val headers = reader.readLine() // read the headers
        val list = reader.lineSequence()
            .filter { it.isNotBlank() }
            .mapIndexed { index, row ->
                val delimiters = listOf(';', ',')
                val delimiter = delimiters.find { row.contains(it) }
                    ?: throw IllegalArgumentException("No valid delimiter found")
                val (accountFromName, accountToName, amount, type, date, description) = row.split(
                    delimiter,
                    ignoreCase = false,
                    limit = 6
                )
                val accountFrom =
                    AppValues.accounts.find { storedAccount ->
                        storedAccount.name.uppercase() == accountFromName.trim().uppercase()
                    }
                val accountTo =
                    AppValues.accounts.find { storedAccount ->
                        storedAccount.name.uppercase() == accountToName.trim().uppercase()
                    }

                accountFrom?.let {
                    TransactionDB(
                        date = date.formatToDateString(),
                        amount = amount.trim().toCurrencyDouble() ?: 0.0,
                        type = getTransactionType(type),
                        accountFrom = accountFrom.id,
                        accountFromName = accountFrom.name,
                        description = "",
                        bankFromIcon = 0,
                        accountTo = accountTo?.id,
                        accountToName = accountTo?.name,
                        bankToIcon = 0
                    )
                }
            }.toList()
        Timber.d("list $list")
        file.delete()
        return list as List<TransactionDB> //TODO need to remove that for sure
    }

    private fun getTransactionType(type: String): TransactionType =
        TransactionType.entries.find { it.name == type.toTransactionTypeFormat() } ?: TransactionType.INVESTMENT

    private fun String.toTransactionTypeFormat(): String {
        return this.uppercase()
            .replace(" ", "_")
    }

}