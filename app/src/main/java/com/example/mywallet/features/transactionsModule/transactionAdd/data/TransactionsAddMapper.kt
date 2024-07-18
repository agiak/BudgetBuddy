package com.example.mywallet.features.transactionsModule.transactionAdd.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

fun TransactionNew.toStoreTransaction(): TransactionDB =
    TransactionDB(
        date = date,
        amount = amount,
        type = type,
        accountFrom = accountFrom,
        accountFromName = accountFromName,
        bankFromIcon = bankFromIcon,
        accountTo = accountTo,
        accountToName = accountToName,
        bankToIcon = bankToIcon,
        description = description,
    )

fun AccountDB.toAccountSelection(): AccountSelection =
    AccountSelection(
        id = id,
        bank = bank,
        name = name,
        balance = balance.toCurrencyBalance(),
    )

fun List<AccountDB>.toAccountSelections(): List<AccountSelection> =
    ArrayList<AccountSelection>().apply {
        this@toAccountSelections.forEach { storedAccount ->
            add(storedAccount.toAccountSelection())
        }
    }