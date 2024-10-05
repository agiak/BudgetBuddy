package com.example.features.account.accountDetails.accountDetails.presentation.viewholders.statics

import androidx.recyclerview.widget.RecyclerView
import com.agcoding.features.account.R
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountDetailsItem
import com.agcoding.features.account.databinding.ItemAccountDetailsStaticsBinding

class AccountStaticsViewHolder(
    private val binding: ItemAccountDetailsStaticsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(statics: AccountDetailsItem.Statics) {
        with(statics.staticsInfo) {
            binding.income.text =
                binding.root.context.getString(R.string.account_details_activity_income, income)
            binding.outcome.text = binding.root.context.getString(
                R.string.account_details_activity_outcome, outcome
            )
            binding.numOfTransactions.text = binding.root.context.getString(
                R.string.account_details_activity_num_transactions,
                numOfTransactions
            )
            binding.change.text = "${binding.root.context.getString(
                R.string.account_details_activity_change,
                lastMonthChange
            )}"
        }
    }
}
