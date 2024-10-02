package com.example.features.transactions.impl.transactions.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.data.mappers.getDetails
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

fun List<TransactionDB>.toTransactions() = ArrayList<Transaction>().apply {
    this@toTransactions.forEach { storedTransaction ->
        add(storedTransaction.toTransaction())
    }
}
