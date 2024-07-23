package com.example.features.rules.salary.data

import com.example.core.data.common.toCurrencyBalance
import com.example.core.data.rule.Rule
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.RuleDB

fun SalaryRuleData.toRuleDB() = RuleDB (
    rule = Rule.Salary,
    salary = salary,
    accountID = account.id,
    accountName = account.name,
)

fun AccountDB.toSelectedAccount(): SelectedAccount =
    SelectedAccount(
        id = id,
        bank = bank,
        name = name,
        balance = balance.toCurrencyBalance(),
    )

fun List<AccountDB>.toAccountSelectedList(): List<SelectedAccount> =
    ArrayList<SelectedAccount>().apply {
        this@toAccountSelectedList.forEach { storedAccount ->
            add(storedAccount.toSelectedAccount())
        }
    }