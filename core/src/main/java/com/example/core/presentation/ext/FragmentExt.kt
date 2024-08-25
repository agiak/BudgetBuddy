package com.example.core.presentation.ext

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.core.data.screens.AuthorizationFlow
import com.example.core.data.screens.MainFlow
import kotlinx.coroutines.launch

fun Fragment.isMainFlow(): Boolean = requireActivity() is MainFlow

fun Fragment.navigateToNextScreen(directions: NavDirections) {
    findNavController().navigate(directions, getNextScreenNavOptions())
}

fun Fragment.navigateToNextScreen(screenDeepLinkUrl: String) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(screenDeepLinkUrl.toUri())
        .build()
    findNavController().navigate(request, getNextScreenNavOptions())
}

fun Fragment.navigateToScreen(screenDeepLinkUrl: String) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(screenDeepLinkUrl.toUri())
        .build()
    findNavController().navigate(request)
}

fun Fragment.navigateBackToScreenWithAnimation(screenDeepLinkUrl: String) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(screenDeepLinkUrl.toUri())
        .build()
    findNavController().navigate(request, getPreviousScreenNavOptions())
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

fun Fragment.onBack() = findNavController().navigateUp()

fun Fragment.startMainFlow() = (requireActivity() as? AuthorizationFlow)?.startMainFlow()

fun Fragment.startAuthFlow() = (requireActivity() as? MainFlow)?.startAuthorizationFlow()