package com.example.mywallet.core.presentation.bank

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mywallet.core.data.bank.Bank
import com.example.mywallet.core.data.bank.BankSelection
import com.example.mywallet.databinding.ItemBankBinding

class BankAdapter(
    private val onClick: (selectedBank: Bank) -> Unit,
) : ListAdapter<BankSelection, BankAdapter.BankViewHolder>(BankDiffCallback()) {

    private lateinit var context: Context
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBankBinding.inflate(inflater, parent, false)
        context = parent.context
        return BankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account, position)
    }

    inner class BankViewHolder(private val binding: ItemBankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bankSection: BankSelection, position: Int) {
            with(bankSection) {
                if (bankSection.isSelected) selectedPosition = position
                binding.logo.isSelected = bankSection.isSelected
                binding.name.text = bank.title
                binding.logo.setImageResource(bank.drawableID)
                Glide.with(context).load(bank.drawableID).circleCrop().into(binding.logo)
                binding.root.setOnClickListener {
                    onItemSelected(position)
                    onClick(bank)
                }
            }
        }
    }

    private fun onItemSelected(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position

        if (previousPosition != -1) {
            currentList[previousPosition].isSelected = false
            notifyItemChanged(previousPosition)
        }

        currentList[position].isSelected = true
        notifyItemChanged(selectedPosition)
    }

    private class BankDiffCallback : DiffUtil.ItemCallback<BankSelection>() {
        override fun areItemsTheSame(oldItem: BankSelection, newItem: BankSelection): Boolean {
            return oldItem.bank.name == newItem.bank.name
        }

        override fun areContentsTheSame(oldItem: BankSelection, newItem: BankSelection): Boolean {
            return oldItem == newItem
        }
    }
}
