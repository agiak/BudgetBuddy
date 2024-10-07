package com.example.core.data.rule

import com.agcoding.core.R

enum class Rule(val title: Int, val description: Int, val iconID: Int) {
    Salary(R.string.rule_salary_title, R.string.rule_salary_description, R.drawable.ic_salary),
    Interest(R.string.rule_interest_title, R.string.rule_interest_description, R.drawable.ic_interest),
}