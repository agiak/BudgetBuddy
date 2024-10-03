package com.example.features.accountAdd.impl.data

import com.example.core.storage.data.AccountDB

fun AccountNew.toAccountDB() = AccountDB(
    name = name,
    bank = bank,
    balance = balance,
)