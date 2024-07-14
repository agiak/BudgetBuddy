package com.example.mywallet.core.presentation.mainflow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.WorkManager
import com.example.common.myutils.addPrintingBackstack
import com.example.common.myutils.hide
import com.example.common.myutils.show
import com.example.mywallet.R
import com.example.mywallet.core.data.application.ScreenType
import com.example.mywallet.core.data.application.identifyScreenType
import com.example.mywallet.core.presentation.ext.navigateFromBottom
import com.example.mywallet.core.presentation.options.MainActivityLifecycleObserver
import com.example.mywallet.core.presentation.options.OptionsScreen
import com.example.mywallet.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerLifecycleObserver()
        setUpNavController()
        initToolbar()
        initObservations()
        setUpWorkManagers()
    }

    private fun initObservations() {
        lifecycleScope.launch {
            viewModel.user.collectLatest { user ->
                user?.let {
                    binding.mainToolbar.profileName.text = user.fullName
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            backButton.setOnClickListener { navController.popBackStack() }
        }

        binding.mainToolbar.apply {
            profileImage.setOnClickListener { navController.navigateFromBottom(R.id.graph_profile) }
        }
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        navController.addPrintingBackstack()
        navController.addOnDestinationChangedListener { _, navigation, _ ->
            handleToolbars(currentDestination = navigation)
        }

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)
    }

    private fun handleToolbars(currentDestination: NavDestination) {
        val screenType = currentDestination.id.identifyScreenType()
        resetToolbars()
        when (screenType) {
            ScreenType.INSIDE -> {
                binding.toolbar.apply {
                    root.show()
                    screenTitle.text = currentDestination.label
                }
                binding.mainToolbar.root.hide()
                binding.navView.hide()
            }

            ScreenType.MENU -> {
                binding.mainToolbar.root.show()
                binding.toolbar.root.hide()
                binding.navView.show()
            }

            ScreenType.MODAL -> {
                binding.mainToolbar.root.hide()
                binding.toolbar.root.hide()
                binding.navView.hide()
            }

            ScreenType.FULL_SCREEN -> {
                binding.mainToolbar.root.hide()
                binding.navView.hide()
                binding.toolbar.root.hide()
            }
            ScreenType.BOTTOM_SHEET -> {}
            ScreenType.OPTIONS -> {
                binding.toolbar.apply {
                    root.show()
                    screenTitle.text = currentDestination.label
                }
                binding.mainToolbar.root.hide()
                binding.navView.hide()
                binding.toolbar.optionsButton.show()
            }
        }
    }

    private fun resetToolbars() {
        binding.toolbar.optionsButton.hide()
    }

    fun setUpOptionButton(optionsScreen: OptionsScreen) {
        binding.toolbar.optionsButton.apply {
            show()
            setImageResource(optionsScreen.optionButtonDrawable)
            setOnClickListener { optionsScreen.onOptionButtonClick() }
        }
    }

    private fun registerLifecycleObserver() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            MainActivityLifecycleObserver(this), true
        )
    }

    private fun setUpWorkManagers() {
        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData("SalaryWork")
            .observe(this) {
                if (!it.isNullOrEmpty()) {
                    val workInfo = it[0]
                    val state = workInfo.state
                    Timber.d("Work state: $state")
                }
            }
    }

}