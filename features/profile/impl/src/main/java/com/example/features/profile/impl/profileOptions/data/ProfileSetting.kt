package com.example.features.profile.impl.profileOptions.data

import com.example.features.profile.impl.R


enum class ProfileSetting(val description: Int, val iconId: Int) {

    EDIT_PROFILE(R.string.profile_setting_edit_profile, R.drawable.ic_edit_profile),
    LANGUAGE(R.string.profile_setting_settings, R.drawable.ic_settings),
    RULES(R.string.profile_setting_rules, R.drawable.ic_rules),
    GUIDE(R.string.profile_setting_show_guide, R.drawable.ic_guide),
}