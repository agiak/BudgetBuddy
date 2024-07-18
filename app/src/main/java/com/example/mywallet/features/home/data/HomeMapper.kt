package com.example.mywallet.features.home.data

import com.example.core.data.common.TransactionType
import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

fun AccountDB.toHomeAccount(): HomeAccount = HomeAccount(
    id = id,
    name = name,
    bank = bank,
    balance = balance.toString(),
    date = createdDate,
)

fun List<AccountDB>.toHomeAccountList(): List<HomeAccount> = ArrayList<HomeAccount>().apply {
    this@toHomeAccountList.forEach { accountDB ->
        add(accountDB.toHomeAccount())
    }
}

fun TransactionDB.toHomeTransaction() = HomeTransaction(
    id = id,
    amount = amount.toCurrencyBalance(),
    details = getDetails()
)

fun List<TransactionDB>.toHomeTransactions(): List<HomeTransaction> =
    ArrayList<HomeTransaction>().apply {
        this@toHomeTransactions.forEach { stroredTransaction ->
            add(stroredTransaction.toHomeTransaction())
        }
    }

private fun TransactionDB.getDetails(): String =
    when(type) {
        TransactionType.MONEY_TRANSFER -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.OUTCOME -> "Charged to $accountFromName"
        TransactionType.INVESTMENT -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.INCOME -> "Income to $accountFromName"
    }
