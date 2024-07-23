package com.example.features.transactionAdd.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.features.transactionAdd.databinding.ItemAccountAddTransactionBinding
import com.example.features.transactionAdd.impl.data.AccountSelection

class TransactionAddAccountsAdapter(
    private val context: Context,
    private val accounts: List<AccountSelection>
) : ArrayAdapter<AccountSelection>(context, 0, accounts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemAccountAddTransactionBinding
        val view: View

        if (convertView == null) {
            binding = ItemAccountAddTransactionBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemAccountAddTransactionBinding
            view = convertView
        }

        val account = accounts[position]

        binding.name.text = account.name
        binding.logo.setImageResource(account.bank.drawableID)
        binding.balance.text = account.balance

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}