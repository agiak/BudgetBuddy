package com.example.mywallet.features.accountModule.account.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywallet.features.accountModule.account.presentation.generalInfo.GeneralInfoFragment
import com.example.mywallet.features.accountModule.account.presentation.info.ActivityFragment

class AccountViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments: List<Fragment> = listOf(
        GeneralInfoFragment(),
        ActivityFragment(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}