package com.example.mywallet.features.account.accountDetails.presentation.viewholders.activity

import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.ext.addSpaceDecorator
import com.example.mywallet.databinding.ItemAccountDetailsActivityBinding
import com.example.mywallet.features.account.accountDetails.data.AccountDetailsItem

class AccountActivityViewHolder(
    private val binding: ItemAccountDetailsActivityBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val transactionAdapter = AccountDetailsActivityAdapter()

    fun bind(recentActivity: AccountDetailsItem.Activity) {
        binding.recentActivityList.apply {
            adapter = transactionAdapter
            addSpaceDecorator(16)
        }
        transactionAdapter.submitList(recentActivity.recentTransactions)
    }
}
