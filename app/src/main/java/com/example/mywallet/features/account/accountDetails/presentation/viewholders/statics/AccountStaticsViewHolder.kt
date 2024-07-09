package com.example.mywallet.features.account.accountDetails.presentation.viewholders.statics

import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.R
import com.example.mywallet.databinding.ItemAccountDetailsStaticsBinding
import com.example.mywallet.features.account.accountDetails.data.AccountDetailsItem

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
