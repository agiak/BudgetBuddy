package com.example.features.account.accountDetails.accountDetails.presentation.viewholders.activity

import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.ext.addSpaceDecorator
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountDetailsItem
import com.agcoding.features.account.databinding.ItemAccountDetailsActivityBinding

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
