package com.example.mywallet.features.rulesModule.ruleSalary.data

import com.example.mywallet.features.rulesModule.rules.data.Rule
import com.example.mywallet.storage.data.RuleDB

fun SalaryRuleData.toRuleDB() = RuleDB (
    rule = Rule.Salary,
    salary = salary,
    accountID = account.id,
    accountName = account.name,
)