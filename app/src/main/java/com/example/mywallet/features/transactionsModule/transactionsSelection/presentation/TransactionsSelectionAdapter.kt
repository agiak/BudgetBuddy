package com.example.mywallet.features.transactionsModule.transactionsSelection.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.databinding.ItemTransactionSelectionBinding
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.TransactionSelection
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.getKey

class TransactionsSelectionAdapter(
    private val onClick: (transaction: TransactionSelection) -> Unit,
) : ListAdapter<TransactionSelection, TransactionsSelectionAdapter.TransactionViewHolder>(
    TransactionDiffCallback()
) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionSelectionBinding.inflate(inflater, parent, false)
        context = parent.context
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, position)
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionSelection, position: Int) {
            with(transaction) {

                binding.btnCheck.isChecked = transaction.isSelected
                binding.amount.text = amount
                binding.details.text = details
                binding.date.text = date
                binding.description.text = description

                binding.btnCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                    transaction.isSelected = isChecked
                    onClick(transaction)
                }
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionSelection>() {
        override fun areItemsTheSame(
            oldItem: TransactionSelection,
            newItem: TransactionSelection
        ): Boolean {
            return oldItem.getKey() == newItem.getKey()
        }

        override fun areContentsTheSame(
            oldItem: TransactionSelection,
            newItem: TransactionSelection
        ): Boolean {
            return oldItem == newItem
        }
    }
}