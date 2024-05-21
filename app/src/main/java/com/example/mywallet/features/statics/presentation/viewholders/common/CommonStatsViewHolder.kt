package com.example.mywallet.features.statics.presentation.viewholders.common

import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.databinding.ItemStaticsCommonStatsBinding
import com.example.mywallet.features.statics.data.StaticsItem

class CommonStatsViewHolder(
    private val binding: ItemStaticsCommonStatsBinding,
): RecyclerView.ViewHolder(binding.root) {

    private val categoryAdapter = CommonStatCategoryAdapter()

    fun bind(commonStat: StaticsItem.CommonStats) {
        binding.categoryStatsList.adapter = categoryAdapter
        categoryAdapter.submitList(commonStat.commonStatCategories)
    }
}
