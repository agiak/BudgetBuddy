package com.example.feature.quide.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature.quide.databinding.ItemGuideBinding
import com.example.features.quide.impl.data.GuideStep

class GuideAdapter(
    private val steps: List<GuideStep>,
) : RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGuideBinding.inflate(inflater, parent, false)
        return GuideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        val account = steps[position]
        holder.bind(account)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    class GuideViewHolder(private val binding: ItemGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(guideStep: GuideStep) {
            binding.description.text = binding.root.context.getString(guideStep.description)
            binding.image.setImageResource(guideStep.iconId)
        }
    }
}
