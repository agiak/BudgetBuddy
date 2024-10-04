package com.example.budgetbuddy.features.home.presentation.activity

import androidx.recyclerview.widget.RecyclerView
import com.budgetbuddy.databinding.ItemHomeActivityBinding
import com.example.core.presentation.ext.addDividerDecorator
import com.example.budgetbuddy.features.home.data.HomeItem

class ActivityViewHolder(
    private val binding: ItemHomeActivityBinding,
    private val navigateToTransaction: (transactionID: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val transactionAdapter = HomeActivityAdapter(
        onClick = { navigateToTransaction(it) }
    )

    fun bind(activityInfo: HomeItem.Activity) {
        binding.transactionsCost.text = "Latest transactions cost: ${activityInfo.transactionsCost}"
        binding.activityList.adapter = transactionAdapter
        binding.activityList.addDividerDecorator(com.example.common.R.color.white)
        transactionAdapter.submitList(activityInfo.list)
    }
}
