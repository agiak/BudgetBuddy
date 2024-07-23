package com.example.features.accounts.filters.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features.accounts.databinding.ItemFilterGroupByBinding
import com.example.features.accounts.impl.filters.data.AccountsFilterGroupBy

class FilterGroupByAdapter(
    private val onClick: (filterGroupBy: AccountsFilterGroupBy?) -> Unit,
) : ListAdapter<AccountsFilterGroupBy, FilterGroupByAdapter.GroupByViewHolder>(GroupByDiffCallback()) {

    private lateinit var context: Context

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupByViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterGroupByBinding.inflate(inflater, parent, false)
        context = parent.context
        return GroupByViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupByViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account, position)
    }

    inner class GroupByViewHolder(private val binding: ItemFilterGroupByBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: AccountsFilterGroupBy, position: Int) {
            with(filter) {

                binding.root.apply {
                    isSelected = filter.isSelected
                    if (isSelected) selectedPosition = position
                    text = context.getString(filter.description)
                    setOnClickListener {
                        onFilterSelection(position)
                        onClick(getSelectedItem(filter))
                    }
                }
            }
        }

        private fun getSelectedItem(itemClicked: AccountsFilterGroupBy): AccountsFilterGroupBy? =
            if (itemClicked.isSelected) itemClicked else null

        private fun onFilterSelection(position: Int) {
            when {
                position.isSamePosition() -> {
                    updateItem(selectedPosition, false)
                    selectedPosition = -1
                }

                else -> {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    if (previousPosition != -1) {
                        updateItem(previousPosition, false)
                    }
                    updateItem(selectedPosition, true)
                }
            }
        }

        private fun Int.isSamePosition() = this == selectedPosition
    }


    private class GroupByDiffCallback : DiffUtil.ItemCallback<AccountsFilterGroupBy>() {
        override fun areItemsTheSame(oldItem: AccountsFilterGroupBy, newItem: AccountsFilterGroupBy): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: AccountsFilterGroupBy, newItem: AccountsFilterGroupBy): Boolean {
            return oldItem == newItem
        }
    }
}

private fun FilterGroupByAdapter.updateItem(position: Int, value: Boolean) {
    currentList[position].isSelected = value
    notifyItemChanged(position)
}