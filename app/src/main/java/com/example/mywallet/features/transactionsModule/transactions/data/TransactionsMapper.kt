package com.example.mywallet.features.transactionsModule.transactions.data

import com.example.core.data.common.TransactionType
import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.TransactionDB

fun TransactionDB.toTransaction(): Transaction =
    Transaction(
        id = id,
        type = type,
        amount = amount.toCurrencyBalance(),
        details = getDetails(),
        date = date,
        bankFromIcon = bankFromIcon,
        bankToIcon = bankToIcon,
        description = description
    )

private fun TransactionDB.getDetails(): String =
    when(type) {
        TransactionType.MONEY_TRANSFER -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.OUTCOME -> "Charged to $accountFromName"
        TransactionType.INVESTMENT -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.INCOME -> "Income to $accountFromName"
    }

fun List<TransactionDB>.toTransactions() = ArrayList<Transaction>().apply {
    this@toTransactions.forEach { storedTransaction ->
        add(storedTransaction.toTransaction())
    }
}
