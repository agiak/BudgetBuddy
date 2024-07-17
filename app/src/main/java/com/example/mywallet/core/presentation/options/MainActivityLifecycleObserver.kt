package com.example.mywallet.core.presentation.options

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mywallet.core.presentation.mainflow.MainActivity
import timber.log.Timber

class MainActivityLifecycleObserver(
    private val mainActivity: MainActivity
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
        super.onFragmentResumed(fm, fragment)
        Timber.d("onFragmentResumed was called")
    }
}