package com.example.mywallet.features.account.accountAdd.data

import com.example.mywallet.storage.data.AccountDB

fun AccountNew.toAccountDB() = AccountDB(
    name = name,
    bank = bank,
    balance = balance,
    createdDate = date
)