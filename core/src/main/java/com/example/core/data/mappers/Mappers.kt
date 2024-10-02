package com.example.core.data.mappers

import com.example.core.data.common.TransactionType
import com.example.core.storage.data.TransactionDB

fun TransactionDB.getDetails(): String =
    when(type) {
        TransactionType.MONEY_TRANSFER -> "Transferred from ${accountFromName} to ${accountToName}"
        TransactionType.OUTCOME -> "Charged to $accountFromName"
        TransactionType.INVESTMENT -> "Invest from ${accountFromName} to ${accountToName}"
        TransactionType.INCOME -> "Income to $accountFromName"
    }