package com.example.features.statics.data.data

import com.example.core.data.bank.Bank
import com.example.core.data.common.TransactionType
import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

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
    description = getDetails(),
    value = amount.toCurrencyBalance()
)

private fun TransactionDB.getDetails(): String =
    when(type) {
        TransactionType.MONEY_TRANSFER -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.OUTCOME -> "Charged to $accountFromName"
        TransactionType.INVESTMENT -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.INCOME -> "Income to $accountFromName"
    }


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

fun Map<Bank, Double>.toMostTrustedBanks() = ArrayList<CommonStatField>().apply {
    var counter = 0
    this@toMostTrustedBanks.forEach { bank, amount ->
        add(
            CommonStatField(
            orderNumber = (counter + 1).toString(),
            description = bank.name,
            value = amount.toCurrencyBalance()
        )
        )
        counter++
    }
}