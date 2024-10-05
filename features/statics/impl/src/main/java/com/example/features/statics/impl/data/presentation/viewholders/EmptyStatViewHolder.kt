package com.example.features.statics.impl.data.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.common.myutils.show
import com.agcoding.features.statics.impl.databinding.ItemEmptyStatsBinding

class EmptyStatViewHolder(
    private val binding: ItemEmptyStatsBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.root.show()
    }
}
