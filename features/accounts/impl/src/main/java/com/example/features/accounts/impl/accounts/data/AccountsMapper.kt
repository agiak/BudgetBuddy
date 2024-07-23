package com.example.features.accounts.impl.accounts.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.storage.data.AccountDB

fun AccountDB.toAccount(): Account = Account(
    id = id,
    name = name,
    bank = bank,
    balance = balance.toCurrencyBalance()
)

fun List<AccountDB>.toAccounts(): List<Account> =
    ArrayList<Account>().apply {
        this@toAccounts.forEach { stroredAccount ->
            add(stroredAccount.toAccount())
        }
    }
