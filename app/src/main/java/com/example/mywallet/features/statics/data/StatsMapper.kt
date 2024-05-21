package com.example.mywallet.features.statics.data

import com.example.mywallet.core.data.common.toCurrencyBalance
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB

fun AccountDB.toMostValuableAccount(position: Int) =
    CommonStatField(
        orderNumber = position.toString(),
        description = name,
        value = balance.toCurrencyBalance()
    )

fun List<AccountDB>.toMostValuableAccounts() = ArrayList<CommonStatField>().apply {
    forEachIndexed { index: Int, account: AccountDB ->
        add(account.toMostValuableAccount(index + 1))
    }
}

fun TransactionDB.toLargeTransaction(position: Int) = CommonStatField(
    orderNumber = position.toString(),
    description = "$description\n$date",
    value = amount.toCurrencyBalance()
)

fun List<TransactionDB>.toLargerTransactions() = ArrayList<CommonStatField>().apply {
    forEachIndexed { index: Int, transaction: TransactionDB ->
        add(transaction.toLargeTransaction(index + 1))
    }
}

fun Map<AccountDB, Int>.toMostUsedAccounts() = ArrayList<CommonStatField>().apply {
    var counter = 0
    this@toMostUsedAccounts.forEach { key, value ->
        add(
            CommonStatField(
                orderNumber = (counter + 1).toString(),
                description = key.name,
                value = value.toString()
            )
        )
        counter++
    }
}