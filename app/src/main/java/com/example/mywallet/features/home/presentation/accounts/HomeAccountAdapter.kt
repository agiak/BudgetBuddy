package com.example.mywallet.features.home.presentation.accounts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common.myutils.loadCircle
import com.example.mywallet.databinding.ItemAccountHomeBinding
import com.example.mywallet.features.home.data.HomeAccount

class HomeAccountAdapter (
    private val onClick: (accountID: Long) -> Unit,
) : ListAdapter<HomeAccount, HomeAccountAdapter.AccountViewHolder>(AccountDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountHomeBinding.inflate(inflater, parent, false)
        context = parent.context
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class AccountViewHolder(private val binding: ItemAccountHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: HomeAccount) {
            with(account){

                binding.name.text = name
                binding.image.loadCircle(bank.drawableID)
                binding.root.setOnClickListener { onClick(account.id) }
            }
        }
    }

    private class AccountDiffCallback : DiffUtil.ItemCallback<HomeAccount>() {
        override fun areItemsTheSame(oldItem: HomeAccount, newItem: HomeAccount): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeAccount, newItem: HomeAccount): Boolean {
            return oldItem == newItem
        }
    }
}
