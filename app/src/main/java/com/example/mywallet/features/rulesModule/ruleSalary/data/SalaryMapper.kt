package com.example.mywallet.features.rulesModule.ruleSalary.data

import com.example.core.data.rule.Rule
import com.example.core.storage.data.RuleDB

fun SalaryRuleData.toRuleDB() = RuleDB (
    rule = Rule.Salary,
    salary = salary,
    accountID = account.id,
    accountName = account.name,
)