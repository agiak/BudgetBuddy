package com.example.features.statics.impl.data.presentation.viewholders.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agcoding.features.statics.impl.databinding.ItemStaticsCommonStatRowBinding
import com.example.features.statics.impl.data.data.CommonStatField

class CommonStatFieldAdapter :
    ListAdapter<CommonStatField, CommonStatFieldAdapter.CommonStatFieldViewHolder>(
        AccountDetailsTransactionDiffCallback()
    ) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonStatFieldViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStaticsCommonStatRowBinding.inflate(inflater, parent, false)
        context = parent.context
        return CommonStatFieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonStatFieldViewHolder, position: Int) {
        val statField = getItem(position)
        holder.bind(statField)
    }

    inner class CommonStatFieldViewHolder(private val binding: ItemStaticsCommonStatRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(statField: CommonStatField) {
            with(statField) {
                binding.number.text = orderNumber
                binding.description.text = description
                binding.value.text = value
            }
        }
    }

    private class AccountDetailsTransactionDiffCallback :
        DiffUtil.ItemCallback<CommonStatField>() {

        override fun areContentsTheSame(
            oldItem: CommonStatField,
            newItem: CommonStatField
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: CommonStatField,
            newItem: CommonStatField
        ): Boolean {
            return oldItem == newItem
        }
    }
}