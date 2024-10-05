package com.example.features.account.accountDetails.editAccount.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.core.data.bank.Bank
import com.agcoding.features.account.R
import com.agcoding.features.account.databinding.ItemSupportedBankBinding

class BankDropDownAdapter(
    context: Context,
    private val items: List<Bank>,
) : ArrayAdapter<Bank>(context, R.layout.item_supported_bank, items) {

    private var filteredBanks: List<Bank> = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSupportedBankBinding
        val view: View

        if (convertView == null) {
            binding = ItemSupportedBankBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemSupportedBankBinding
            view = convertView
        }

        val supportedBank = items[position]

        binding.name.text = supportedBank.title
        binding.logo.setImageResource(supportedBank.drawableID)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return filteredBanks.size
    }

    override fun getItem(position: Int): Bank? {
        return filteredBanks[position]
    }

    override fun getFilter(): Filter {
        return accountFilter
    }

    private val accountFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint.isNullOrEmpty()) {
                filterResults.values = items
                filterResults.count = items.size
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                val filteredList = items.filter {
                    it.name.lowercase().contains(filterPattern)
                }
                filterResults.values = filteredList
                filterResults.count = filteredList.size
            }
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredBanks = results?.values as List<Bank>
            notifyDataSetChanged()
        }
    }
}