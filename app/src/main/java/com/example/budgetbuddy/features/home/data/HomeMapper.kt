package com.example.budgetbuddy.features.home.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.data.mappers.getDetails
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

fun AccountDB.toHomeAccount(): HomeAccount = HomeAccount(
    id = id,
    name = name,
    bank = bank,
    balance = balance.toString(),
)

fun List<AccountDB>.toHomeAccountList(): List<HomeAccount> = ArrayList<HomeAccount>().apply {
    this@toHomeAccountList.forEach { accountDB ->
        add(accountDB.toHomeAccount())
    }
}

fun TransactionDB.toHomeTransaction() = HomeTransaction(
    id = id,
    amount = amount.toCurrencyBalance(),
    details = getDetails(),
    date = date,
)

fun List<TransactionDB>.toHomeTransactions(): List<HomeTransaction> =
    ArrayList<HomeTransaction>().apply {
        this@toHomeTransactions.forEach { stroredTransaction ->
            add(stroredTransaction.toHomeTransaction())
        }
    }
