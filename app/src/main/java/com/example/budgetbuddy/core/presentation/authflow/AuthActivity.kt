package com.example.budgetbuddy.core.presentation.authflow

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.agcoding.budgetbuddy.databinding.ActivityAuthBinding
import com.example.common.myutils.addPrintingBackstack
import com.example.core.data.screens.AuthorizationFlow
import com.example.core.presentation.BackPressHandler
import com.example.budgetbuddy.core.presentation.openMainFlow
import com.example.budgetbuddy.features.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.agcoding.budgetbuddy.R

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(), AuthorizationFlow {

    private lateinit var binding: ActivityAuthBinding

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpSplashScreenApi()
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavController()
        initToolbar()

        setBackPressFunctionality()
    }

    private fun setBackPressFunctionality() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                            ?.childFragmentManager?.primaryNavigationFragment
                    currentFragment?.let { f ->
                        when (f) {
                            is BackPressHandler -> f.onBackPress()
                            else -> navController.navigateUp()
                        }
                    }

                }
            })
    }

    private fun setUpSplashScreenApi() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.showSplash.value
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            backButton.setOnClickListener { navController.navigateUp() }
        }
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        navController.addPrintingBackstack()
        navController.addOnDestinationChangedListener { _, navigation, _ ->
            binding.toolbar.apply {
                root.isVisible = false
                screenTitle.text = ""
            }
        }
    }

    override fun startMainFlow() {
        this.openMainFlow()
    }
}