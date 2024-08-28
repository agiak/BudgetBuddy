package com.example.features.impl.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features.impl.data.MoreItem
import com.example.features.impl.databinding.ItemMoreBinding

class MoreAdapter(
    private val onClick: (selectedBank: MoreItem) -> Unit,
) : ListAdapter<MoreItem, MoreAdapter.MoreViewHolder>(MoreItemDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoreBinding.inflate(inflater, parent, false)
        context = parent.context
        return MoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        val moreItem = getItem(position)
        holder.bind(moreItem)
    }

    inner class MoreViewHolder(private val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moreItem: MoreItem) {
            binding.label.text = context.getString(moreItem.label)
            binding.btnItem.setImageResource(moreItem.icon)
            with(moreItem) {
                binding.btnItem.setOnClickListener {
                    onClick(moreItem)
                }
            }
        }
    }

    private class MoreItemDiffCallback : DiffUtil.ItemCallback<MoreItem>() {
        override fun areItemsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
            return oldItem == newItem
        }
    }
}