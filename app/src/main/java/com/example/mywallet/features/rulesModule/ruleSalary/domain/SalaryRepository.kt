package com.example.mywallet.features.rulesModule.ruleSalary.domain

import com.example.core.storage.data.RuleDB

interface SalaryRepository {

    suspend fun fetchCurrentSalaryRule(): RuleDB?

    suspend fun deleteRule()

    suspend fun enableRule(ruleDB: RuleDB)
}