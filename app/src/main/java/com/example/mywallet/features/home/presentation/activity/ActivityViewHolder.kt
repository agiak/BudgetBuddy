package com.example.mywallet.features.home.presentation.activity

import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.databinding.ItemHomeActivityBinding
import com.example.mywallet.features.home.data.HomeItem
import timber.log.Timber

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
        transactionAdapter.submitList(activityInfo.list)
    }
}
