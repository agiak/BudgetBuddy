package com.example.features.statics.impl.data.presentation

import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.data.presentation.viewholders.EmptyStatViewHolder
import com.example.features.statics.impl.data.presentation.viewholders.StaticsItemViewHolderFactory
import com.example.features.statics.impl.data.presentation.viewholders.charts.InvestmentProgressViewHolder
import com.example.features.statics.impl.data.presentation.viewholders.common.CommonStatsViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StaticsAdapter(
    private val viewHolderFactories: List<StaticsItemViewHolderFactory<out StaticsItem>>
) : ListAdapter<StaticsItem, RecyclerView.ViewHolder>(StaticsItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_COMMON = 1
        private const val VIEW_TYPE_INVESTMENT_PROGRESS = 2
        private const val VIEW_TYPE_EMPTY = 3
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is StaticsItem.CommonStats -> VIEW_TYPE_COMMON
            is StaticsItem.InvestmentProgress -> VIEW_TYPE_INVESTMENT_PROGRESS
            is StaticsItem.EmptyStats -> VIEW_TYPE_EMPTY
            else -> 0
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COMMON -> (viewHolderFactories[0] as StaticsItemViewHolderFactory<*>).create(
                parent
            )

            VIEW_TYPE_INVESTMENT_PROGRESS -> (viewHolderFactories[1] as StaticsItemViewHolderFactory<*>).create(
                parent
            )

            VIEW_TYPE_EMPTY -> (viewHolderFactories[2] as StaticsItemViewHolderFactory<*>).create(
                parent
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val coroutineScope =
            holder.itemView.findViewTreeLifecycleOwner()?.lifecycleScope ?: CoroutineScope(
                Dispatchers.IO
            )

        coroutineScope.launch {
            when (val item = getItem(position)) {
                is StaticsItem.CommonStats -> (holder as CommonStatsViewHolder).bind(item)
                is StaticsItem.InvestmentProgress -> (holder as InvestmentProgressViewHolder).bind(
                    item
                )
                is StaticsItem.EmptyStats -> (holder as EmptyStatViewHolder).bind()
            }
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