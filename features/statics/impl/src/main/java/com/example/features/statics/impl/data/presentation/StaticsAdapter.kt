package com.example.features.statics.impl.data.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.data.presentation.viewholders.StaticsItemViewHolderFactory
import com.example.features.statics.impl.data.presentation.viewholders.common.CommonStatsViewHolder

class StaticsAdapter(
    private val viewHolderFactories: List<StaticsItemViewHolderFactory<out StaticsItem>>
) : ListAdapter<StaticsItem, RecyclerView.ViewHolder>(StaticsItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_COMMON = 1
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is StaticsItem.CommonStats -> VIEW_TYPE_COMMON
            else -> 0
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COMMON -> (viewHolderFactories[0] as StaticsItemViewHolderFactory<*>).create(
                parent
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is StaticsItem.CommonStats -> (holder as CommonStatsViewHolder).bind(item)
        }
    }

    private class StaticsItemDiffCallback : DiffUtil.ItemCallback<StaticsItem>() {
        override fun areItemsTheSame(oldItem: StaticsItem, newItem: StaticsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StaticsItem, newItem: StaticsItem): Boolean {
            return oldItem == newItem
        }
    }
}