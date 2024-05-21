package com.example.mywallet.features.rulesModule.ruleSalary.data

import com.example.mywallet.features.transactionsModule.transactionAdd.data.AccountSelection

data class SalaryRuleData(
    val salary: Double,
    val account: AccountSelection
)
