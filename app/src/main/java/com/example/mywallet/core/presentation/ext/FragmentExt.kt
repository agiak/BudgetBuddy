package com.example.mywallet.core.presentation.ext

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mywallet.core.presentation.mainflow.MainActivity
import kotlinx.coroutines.launch

fun Fragment.isMainFlow(): Boolean = requireActivity() is MainActivity

fun Fragment.navigateToNextScreen(directions: NavDirections) {
    findNavController().navigate(directions, getNextScreenNavOptions())
}

fun Fragment.launchWhenResumed(block: suspend () -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            block()
        }
    }
}

fun Fragment.isPermissionGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED