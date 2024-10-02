package com.example.features.account.accountDetails.impl.accountDetails.data

import com.example.core.data.account.AccountDetails
import com.example.core.data.common.toCurrencyBalance
import com.example.core.data.mappers.getDetails
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

fun TransactionDB.toAccountTransaction(): AccountTransaction =
    AccountTransaction(
        id = id,
        amount = amount.toCurrencyBalance(),
        date = date,
        details = getDetails(),
        description = description
    )

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