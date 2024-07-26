package com.example.features.profile.impl.profileOptions.data

import com.example.features.profile.impl.R


enum class ProfileSetting(val description: Int, val iconId: Int) {

    EDIT_PROFILE(R.string.profile_setting_edit_profile, R.drawable.ic_edit_profile),
    LANGUAGE(R.string.profile_setting_settings, com.example.core.R.drawable.ic_logo),
    RULES(R.string.profile_setting_rules, R.drawable.ic_rules),
    MAIN_ACCOUNTS(R.string.profile_setting_main_accounts, com.example.core.R.drawable.ic_logo),
}