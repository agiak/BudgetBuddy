package com.example.quicklogin.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.setLightStatusBars
import com.example.core.data.screens.AuthorizationFlow
import com.example.core.presentation.createAccountScreen
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToScreen
import com.example.core.presentation.guideScreen
import com.example.core.presentation.registerScreen
import com.example.feature.quicklogin.R
import com.example.feature.quicklogin.databinding.FragmentQuickLoginBinding
import com.example.features.quicklogin.impl.data.QuickLoginState
import com.example.features.quicklogin.impl.data.UserState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class QuickLoginFragment : Fragment() {

    private val viewModel: QuickLoginViewModel by viewModels()

    private var _binding: FragmentQuickLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQuickLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.state.collectLatest { state->
                when(state) {
                    QuickLoginState.Loading -> {
                        binding.lblStatus.text = getString(R.string.quick_login_loading)
                    }
                    is QuickLoginState.Result -> {
                        Timber.d("user state is ${state.userState}")
                        when(state.userState) {
                            UserState.Uneducated -> navigateToGuide()
                            UserState.Unregister -> navigateToRegister()
                            UserState.Unsetted -> navigateToCreateAccount()
                            UserState.Valid -> (requireActivity() as AuthorizationFlow).startMainFlow()
                        }
                    }
                    is QuickLoginState.Error -> {
                        binding.lblStatus.text = state.errorMessage
                    }
                }
            }
        }
    }

    private fun navigateToRegister() = navigateToScreen(registerScreen)

    private fun navigateToGuide() = navigateToScreen(guideScreen)

    private fun navigateToCreateAccount() = navigateToScreen(createAccountScreen)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}