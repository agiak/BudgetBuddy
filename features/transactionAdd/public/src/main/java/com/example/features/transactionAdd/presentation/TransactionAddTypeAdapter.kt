package com.example.features.transactionAdd.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.core.data.common.TransactionType
import com.example.features.transactionAdd.databinding.ItemTransactionAddTypeBinding

class TransactionAddTypeAdapter(
    private val context: Context,
    private val types: List<TransactionType>
) : ArrayAdapter<TransactionType>(context, 0, types) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemTransactionAddTypeBinding
        val view: View

        if (convertView == null) {
            binding = ItemTransactionAddTypeBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemTransactionAddTypeBinding
            view = convertView
        }

        val type = types[position]
        binding.title.text = context.getString(type.description)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}