package com.example.budgetbuddy.features.home.presentation.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agcoding.budgetbuddy.databinding.ItemHomeActivityTransactionBinding
import com.example.budgetbuddy.features.home.data.HomeTransaction

class HomeActivityAdapter (
    private val onClick: (transactionID: Long) -> Unit,
) : ListAdapter<HomeTransaction, HomeActivityAdapter.TransactionViewHolder>(
    HomeTransactionDiffCallback()
) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeActivityTransactionBinding.inflate(inflater, parent, false)
        context = parent.context
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    inner class TransactionViewHolder(private val binding: ItemHomeActivityTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: HomeTransaction) {
            with(transaction){
                binding.balance.text = amount
                binding.details.text = details
                binding.date.text = date
                binding.root.setOnClickListener { onClick(id) }
            }
        }
    }

    private class HomeTransactionDiffCallback : DiffUtil.ItemCallback<HomeTransaction>() {

        override fun areContentsTheSame(oldItem: HomeTransaction, newItem: HomeTransaction): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: HomeTransaction,
            newItem: HomeTransaction
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
