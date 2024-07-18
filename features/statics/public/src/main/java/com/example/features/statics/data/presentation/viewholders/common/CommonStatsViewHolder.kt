package com.example.features.statics.data.presentation.viewholders.common

import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.data.data.StaticsItem
import com.example.features.statics.databinding.ItemStaticsCommonStatsBinding
import com.example.mywallet.features.statics.presentation.viewholders.common.CommonStatCategoryAdapter

class CommonStatsViewHolder(
    private val binding: ItemStaticsCommonStatsBinding,
): RecyclerView.ViewHolder(binding.root) {

    private val categoryAdapter = CommonStatCategoryAdapter()

    fun bind(commonStat: StaticsItem.CommonStats) {
        binding.categoryStatsList.adapter = categoryAdapter
        categoryAdapter.submitList(commonStat.commonStatCategories)
    }
}
