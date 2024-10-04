package com.example.features.accounts.filters.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features.accounts.databinding.ItemFilterAccountsOrderByBinding
import com.example.features.accounts.impl.filters.data.AccountsFilterOrderBy

class FilterOrderByAdapter(
    private val onClick: (filterGroupBy: AccountsFilterOrderBy) -> Unit,
) : ListAdapter<AccountsFilterOrderBy, FilterOrderByAdapter.GroupByViewHolder>(OrderByDiffCallback()) {

    private lateinit var context: Context

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupByViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterAccountsOrderByBinding.inflate(inflater, parent, false)
        context = parent.context
        return GroupByViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupByViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account, position)
    }

    inner class GroupByViewHolder(private val binding: ItemFilterAccountsOrderByBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: AccountsFilterOrderBy, position: Int) {
            binding.root.apply {
                isChecked = filter.isSelected
                if (filter.isSelected) selectedPosition = position
                text = context.getString(filter.description)
                setOnClickListener {
                    onClick(filter)
                    onFilterSelection(position)
                }
            }
        }

        private fun onFilterSelection(position: Int) {
            val previousPosition = selectedPosition
            selectedPosition = position

            if (previousPosition != -1) {
                currentList[previousPosition].isSelected = false
                notifyItemChanged(previousPosition)
            }

            currentList[position].isSelected = true
            notifyItemChanged(selectedPosition)
        }
    }


    private class OrderByDiffCallback : DiffUtil.ItemCallback<AccountsFilterOrderBy>() {
        override fun areItemsTheSame(
            oldItem: AccountsFilterOrderBy,
            newItem: AccountsFilterOrderBy
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: AccountsFilterOrderBy,
            newItem: AccountsFilterOrderBy
        ): Boolean {
            return oldItem == newItem
        }
    }
}
