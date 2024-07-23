package com.example.mywallet.features.rulesModule.ruleSalary.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.mywallet.databinding.ItemAccountAddTransactionBinding
import com.example.mywallet.features.rulesModule.ruleSalary.data.SelectedAccount

class SalaryAccountsAdapter(
    private val context: Context,
    private val accounts: List<SelectedAccount>
) : ArrayAdapter<SelectedAccount>(context, 0, accounts) {

    private var filteredAccounts: List<SelectedAccount> = accounts

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemAccountAddTransactionBinding
        val view: View

        if (convertView == null) {
            binding = ItemAccountAddTransactionBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemAccountAddTransactionBinding
            view = convertView
        }

        val account = filteredAccounts[position]

        binding.name.text = account.name
        binding.logo.setImageResource(account.bank.drawableID)
        binding.balance.text = account.balance

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return filteredAccounts.size
    }

    override fun getItem(position: Int): SelectedAccount? {
        return filteredAccounts[position]
    }

    override fun getFilter(): Filter {
        return accountFilter
    }

    private val accountFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint.isNullOrEmpty()) {
                filterResults.values = accounts
                filterResults.count = accounts.size
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                val filteredList = accounts.filter {
                    it.name.lowercase().contains(filterPattern)
                }
                filterResults.values = filteredList
                filterResults.count = filteredList.size
            }
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredAccounts = results?.values as List<SelectedAccount>
            notifyDataSetChanged()
        }
    }
}