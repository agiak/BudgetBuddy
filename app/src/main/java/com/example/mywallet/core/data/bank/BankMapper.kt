package com.example.mywallet.core.data.bank

fun List<Bank>.toBankSelectionList(selectedBank: Bank? = null): List<BankSelection> =
    ArrayList<BankSelection>().apply {
        Bank.entries.forEach { bank ->
            add(BankSelection(bank = bank, isSelected = bank == selectedBank))
        }
    }