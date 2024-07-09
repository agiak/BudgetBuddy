package com.example.mywallet.features.account.accountDetails.data

import com.example.mywallet.core.data.common.TransactionType
import com.example.mywallet.core.data.common.toCurrencyBalance
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB

fun TransactionDB.toAccountTransaction(): AccountTransaction =
    AccountTransaction(
        id = id,
        amount = amount.toCurrencyBalance(),
        date = date,
        details = getDetails(),
        description = description
    )

private fun TransactionDB.getDetails(): String =
    when(type) {
        TransactionType.MONEY_TRANSFER -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.OUTCOME -> "Charged to $accountFromName"
        TransactionType.INVESTMENT -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.INCOME -> "Income to $accountFromName"
    }

fun List<TransactionDB>.toAccountTransactions(): List<AccountTransaction> =
    ArrayList<AccountTransaction>().apply {
        this@toAccountTransactions.forEach { storedTransaction ->
            add(storedTransaction.toAccountTransaction())
        }
    }

fun AccountDB.toAccountDetails(): AccountDetails = AccountDetails(
    id = id,
    balance = balance.toString(),
    name = name,
    date = createdDate,
    bank = bank
)