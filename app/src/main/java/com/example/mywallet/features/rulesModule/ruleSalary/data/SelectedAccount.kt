package com.example.mywallet.features.rulesModule.ruleSalary.data

import com.example.core.data.bank.Bank

data class SelectedAccount(
    val id: Long,
    val bank: Bank,
    val name: String,
    val balance: String,
) {
    override fun toString(): String = name
}
