package com.example.features.transactionAdd.impl.data

import com.example.core.data.bank.Bank

data class AccountSelection(
    val id: Long,
    val bank: Bank,
    val name: String,
    val balance: String,
) {
    override fun toString(): String = name
}
