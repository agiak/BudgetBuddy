package com.example.features.transactionsViaFile.impl.transactionsSelection.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.TransactionDB
import timber.log.Timber

fun TransactionDB.toTransactionSelection() = TransactionSelection(
    details = "$accountFromName to $accountToName",
    amount = amount.toCurrencyBalance(),
    date = date,
    description = description,
    isSelected = true,
    type = type,
)

fun List<TransactionDB>.toTransactionSelectionList() = ArrayList<TransactionSelection>().apply {
    this@toTransactionSelectionList.forEachIndexed { index, transactionDB ->
        Timber.d("try to cast transaction at index $index and this is $transactionDB")
        add(transactionDB.toTransactionSelection())
    }
}

fun TransactionDB.getKey() =
    "$accountFromName to ${accountToName}_${amount.toCurrencyBalance()}_$date"

fun TransactionSelection.getKey() = "${details}_${amount}_$date"

fun TransactionDB.isEqual(transactionSelection: TransactionSelection) =
    this.getKey() == transactionSelection.getKey()