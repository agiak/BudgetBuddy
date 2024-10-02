package com.example.features.transactions.transactions.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features.transactions.databinding.ItemTransactionBinding
import com.example.features.transactions.impl.transactions.data.Transaction

class TransactionsAdapter(
    private val onClick: (transactionID: Long) -> Unit,
    private val onDelete: (transactionID: Long) -> Unit,
) : ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        context = parent.context
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transition: Transaction) {
            with(transition) {

                binding.amount.text = amount
                binding.details.text = details
                binding.date.text = date
                binding.description.apply {
                    text = description
                    isVisible = description.isNotEmpty()
                }
                binding.type.text = context.getString(type.description)

                binding.root.setOnClickListener { onClick(transition.id) }
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
}
