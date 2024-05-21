package com.example.mywallet.core.data.common

import com.example.mywallet.storage.data.AccountDB

object AppValues {

    var hasTransactions = false

    var accounts: List<AccountDB> = emptyList()
}