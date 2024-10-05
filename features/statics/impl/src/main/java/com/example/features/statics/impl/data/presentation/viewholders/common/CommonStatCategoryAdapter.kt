package com.example.features.statics.impl.data.presentation.viewholders.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agcoding.features.statics.impl.databinding.ItemStaticsCommonCategoryBinding
import com.example.core.presentation.ext.addDividerDecorator
import com.example.features.statics.impl.data.data.CommonStatCategory

class CommonStatCategoryAdapter :
    ListAdapter<CommonStatCategory, CommonStatCategoryAdapter.CommonStatViewHolder>(
        CommonStatDiffCallback()
    ) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonStatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStaticsCommonCategoryBinding.inflate(inflater, parent, false)
        context = parent.context
        return CommonStatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonStatViewHolder, position: Int) {
        val commonStatCategory = getItem(position)
        holder.bind(commonStatCategory)
    }

    inner class CommonStatViewHolder(private val binding: ItemStaticsCommonCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(commonStatCategory: CommonStatCategory) {
            with(commonStatCategory) {
                binding.title.text = context.getString(commonStatCategory.title)
                val fieldAdapter = CommonStatFieldAdapter()
                binding.fieldList.apply {
                    adapter = fieldAdapter
                    addDividerDecorator()
                }
                fieldAdapter.submitList(commonStatCategory.results)
            }
        }
    }

    private class CommonStatDiffCallback :
        DiffUtil.ItemCallback<CommonStatCategory>() {

        override fun areContentsTheSame(
            oldItem: CommonStatCategory,
            newItem: CommonStatCategory
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: CommonStatCategory,
            newItem: CommonStatCategory
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}