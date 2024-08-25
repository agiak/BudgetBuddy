package com.example.features.statics.impl.data.data

import com.example.core.data.common.TransactionType
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

data class StatsData(
    val accounts: List<AccountDB>,
    val transactions: List<TransactionDB>
) {
    fun isEmpty(): Boolean =
        accounts.isEmpty() ||
                transactions.isEmpty() ||
                !transactions.any { it.type == TransactionType.INVESTMENT }
}
