package com.example.features.accounts.impl.filters.data

import com.agcoding.features.accounts.impl.R

enum class AccountsFilterOrderBy(val description: Int, var isSelected: Boolean = false) {
    DESCENDING(R.string.filter_accounts_order_by_descending, false),
    ASCENDING(R.string.filter_accounts_order_by_ascending, false),
    DATE_ADDED(R.string.filter_accounts_order_by_date_adding, true),;

    override fun toString(): String {
        return "AccountsFilterOrderBy(description=$description, isSelected=$isSelected)"
    }
}