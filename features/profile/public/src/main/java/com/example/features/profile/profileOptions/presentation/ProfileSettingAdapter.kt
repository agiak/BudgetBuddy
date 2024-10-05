package com.example.features.profile.profileOptions.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agcoding.features.profile.databinding.ItemProfileSettingBinding
import com.example.features.profile.impl.profileOptions.data.ProfileSetting

class ProfileSettingAdapter (
    private val onClick: (item: ProfileSetting) -> Unit
): ListAdapter<ProfileSetting, ProfileSettingAdapter.ProfileSettingViewHolder>(
    ProfileSettingDiffCallback()
) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileSettingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileSettingBinding.inflate(inflater, parent, false)
        context = parent.context
        return ProfileSettingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileSettingViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class ProfileSettingViewHolder(private val binding: ItemProfileSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(setting: ProfileSetting) {
            binding.image.setImageResource(setting.iconId)
            binding.description.text = context.getString(setting.description)
            binding.root.setOnClickListener { onClick(setting) }
        }
    }

    private class ProfileSettingDiffCallback : DiffUtil.ItemCallback<ProfileSetting>() {
        override fun areItemsTheSame(oldItem: ProfileSetting, newItem: ProfileSetting): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProfileSetting, newItem: ProfileSetting): Boolean {
            return oldItem == newItem
        }
    }
}