package com.example.mywallet.core.presentation.authflow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.common.myutils.addPrintingBackstack
import com.example.mywallet.features.activation.splash.SplashViewModel
import com.example.mywallet.R
import com.example.mywallet.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

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
            backButton.setOnClickListener { navController.popBackStack() }
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
                screenTitle.text = navigation.label
            }
        }
    }
}