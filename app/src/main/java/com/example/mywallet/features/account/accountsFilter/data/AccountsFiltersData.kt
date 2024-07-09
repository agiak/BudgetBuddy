package com.example.mywallet.features.account.accountsFilter.data

data class AccountsFiltersData(
    val groupBySelections: List<AccountsFilterGroupBy>,
    val orderBySelections: List<AccountsFilterOrderBy>
)