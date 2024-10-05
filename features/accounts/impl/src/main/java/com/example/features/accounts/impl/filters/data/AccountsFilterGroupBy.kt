package com.example.features.accounts.impl.filters.data

import com.agcoding.features.accounts.impl.R

enum class AccountsFilterGroupBy(val description: Int, var isSelected: Boolean = false) {
    BY_BANK(R.string.filter_accounts_group_by_bank, true),
}