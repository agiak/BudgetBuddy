package com.example.features.statics.impl.data.presentation.viewholders.common

import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.impl.data.data.StaticsItem
import com.agcoding.features.statics.impl.databinding.ItemStaticsCommonStatsBinding
import timber.log.Timber

class CommonStatsViewHolder(
    private val binding: ItemStaticsCommonStatsBinding,
): RecyclerView.ViewHolder(binding.root) {

    private val categoryAdapter = CommonStatCategoryAdapter()

    fun bind(commonStat: StaticsItem.CommonStats) {
        try {
            binding.categoryStatsList.adapter = categoryAdapter
            categoryAdapter.submitList(commonStat.commonStatCategories)
        } catch (ex: IllegalStateException) {
            Timber.e(ex)
        }
    }
}
