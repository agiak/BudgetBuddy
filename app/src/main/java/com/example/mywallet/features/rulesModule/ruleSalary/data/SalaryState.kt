package com.example.mywallet.features.rulesModule.ruleSalary.data

sealed class SalaryState {
    data object Loading: SalaryState()
    data object NoRuleFound: SalaryState()
    data class CurrentSalaryRule(val amount: Double, val account: SalaryAccount): SalaryState()
    data object RuleActivated: SalaryState()
}