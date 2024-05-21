package com.example.mywallet.features.transactionsModule.transactionAdd.data

import com.example.mywallet.core.data.bank.Bank

data class AccountSelection(
    val id: Long,
    val bank: Bank,
    val name: String,
    val balance: String,
) {
    override fun toString(): String = name
}
