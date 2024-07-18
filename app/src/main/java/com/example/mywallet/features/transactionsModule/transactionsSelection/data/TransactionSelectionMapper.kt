package com.example.mywallet.features.transactionsModule.transactionsSelection.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.TransactionDB

fun TransactionDB.toTransactionSelection() = TransactionSelection(
    details = "$accountFromName to $accountToName",
    amount = amount.toCurrencyBalance(),
    date = date,
    description = description,
    isSelected = true
)

fun List<TransactionDB>.toTransactionSelectionList() = ArrayList<TransactionSelection>().apply {
    this@toTransactionSelectionList.forEach { storedTransaction ->
        add(storedTransaction.toTransactionSelection())
    }
}

fun TransactionDB.getKey() =
    "$accountFromName to ${accountToName}_${amount.toCurrencyBalance()}_$date"

fun TransactionSelection.getKey() = "${details}_${amount}_$date"

fun TransactionDB.isEqual(transactionSelection: TransactionSelection) =
    this.getKey() == transactionSelection.getKey()