package com.example.core.data.common

import com.agcoding.core.R

enum class TransactionType(val description: Int) {
    MONEY_TRANSFER(R.string.transaction_type_money_transfer),
    INVESTMENT(R.string.transaction_type_investment),
    INCOME(R.string.transaction_type_income),
    OUTCOME(R.string.transaction_type_outcome),;
}

fun TransactionType.isOneAccountTransaction(): Boolean =
    this == TransactionType.INCOME || this == TransactionType.OUTCOME

fun TransactionType.isInternalTransaction(): Boolean =
    this == TransactionType.MONEY_TRANSFER || this == TransactionType.INVESTMENT