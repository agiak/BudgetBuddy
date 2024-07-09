package com.example.mywallet.features.account.accountDetails.presentation.viewholders.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.databinding.ItemAccountDetailsActivityTransactionBinding
import com.example.mywallet.features.account.accountDetails.data.AccountTransaction

class AccountDetailsActivityAdapter :
    ListAdapter<AccountTransaction, AccountDetailsActivityAdapter.TransactionViewHolder>(
        AccountDetailsTransactionDiffCallback()
    ) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountDetailsActivityTransactionBinding.inflate(inflater, parent, false)
        context = parent.context
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    inner class TransactionViewHolder(private val binding: ItemAccountDetailsActivityTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: AccountTransaction) {
            with(transaction) {
                binding.amount.text = amount
                binding.details.text = details
                binding.date.text = date
                binding.description.text = description
            }
        }
    }

    private class AccountDetailsTransactionDiffCallback :
        DiffUtil.ItemCallback<AccountTransaction>() {

        override fun areContentsTheSame(
            oldItem: AccountTransaction,
            newItem: AccountTransaction
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: AccountTransaction,
            newItem: AccountTransaction
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
