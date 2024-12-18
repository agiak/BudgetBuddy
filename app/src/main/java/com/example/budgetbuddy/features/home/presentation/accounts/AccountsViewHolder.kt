package com.example.budgetbuddy.features.home.presentation.accounts

import androidx.recyclerview.widget.RecyclerView
import com.agcoding.budgetbuddy.databinding.ItemHomeAccountsBinding
import com.example.budgetbuddy.features.home.data.HomeItem

class AccountsViewHolder(
    private val binding: ItemHomeAccountsBinding,
    private val navigateToAllAccounts: () -> Unit,
    private val navigateToAccount: (accountID: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val accountAdapter = HomeAccountAdapter(
        onClick = { navigateToAccount(it) },
    )

    fun bind(accountInfo: HomeItem.Accounts) {
        binding.accountsLbl.setOnClickListener { navigateToAllAccounts() }
        binding.listAccounts.adapter = accountAdapter.apply {
            submitList(accountInfo.accounts)
        }
    }
}
