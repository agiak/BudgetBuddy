package com.example.features.statics.impl.data.presentation.viewholders.common

import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.databinding.ItemStaticsCommonStatsBinding

class CommonStatsViewHolder(
    private val binding: ItemStaticsCommonStatsBinding,
): RecyclerView.ViewHolder(binding.root) {

    private val categoryAdapter = CommonStatCategoryAdapter()

    fun bind(commonStat: StaticsItem.CommonStats) {
        binding.categoryStatsList.adapter = categoryAdapter
        categoryAdapter.submitList(commonStat.commonStatCategories)
    }
}
