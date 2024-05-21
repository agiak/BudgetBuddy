package com.example.mywallet.features.home.presentation.summary

import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.databinding.ItemHomeSummaryBinding
import com.example.mywallet.features.home.data.HomeItem

class SummaryViewHolder(
    private val binding: ItemHomeSummaryBinding,
    private val addMoneyClick: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(summaryInfo: HomeItem.Summary) {
//        binding.btnAddMoney.setOnClickListener { addMoneyClick() }
        binding.totalBalanceField.text = summaryInfo.totalBalance
        binding.transformationField.text = summaryInfo.lastChange
    }
}
