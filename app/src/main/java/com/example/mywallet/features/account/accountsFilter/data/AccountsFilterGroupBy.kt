package com.example.mywallet.features.account.accountsFilter.data

import com.example.mywallet.R

enum class AccountsFilterGroupBy(val description: Int, var isSelected: Boolean = false) {
    BY_BANK(R.string.filter_accounts_group_by_bank, true),
}