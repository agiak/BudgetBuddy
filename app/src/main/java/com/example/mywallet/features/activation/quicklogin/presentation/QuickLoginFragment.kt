package com.example.mywallet.features.activation.quicklogin.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.setLightStatusBars
import com.example.mywallet.core.presentation.ext.startMainFlow
import com.example.mywallet.databinding.FragmentQuickLoginBinding
import com.example.mywallet.features.activation.quicklogin.data.QuickLoginState
import com.example.mywallet.features.activation.quicklogin.data.UserState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            viewModel.state.collectLatest { state->
                when(state) {
                    QuickLoginState.Loading -> {
                        binding.lblStatus.text = "Loading"
                    }
                    is QuickLoginState.Result -> {
                        Timber.d("user state is ${state.userState}")
                        when(state.userState) {
                            UserState.Uneducated -> navigateToGuide()
                            UserState.Unregister -> navigateToRegister()
                            UserState.Unsetted -> navigateToCreateAccount()
                            UserState.Valid -> requireContext().startMainFlow()
                        }
                    }
                    is QuickLoginState.Error -> {
                        binding.lblStatus.text = state.errorMessage
                    }
                }
            }
        }
    }

    private fun navigateToRegister() =
        findNavController().navigate(QuickLoginFragmentDirections.actionNavigationQuickLoginToNavigationRegister())

    private fun navigateToGuide() =
        findNavController().navigate(QuickLoginFragmentDirections.actionNavigationQuickLoginToNavigationQuide())

    private fun navigateToCreateAccount() =
        findNavController().navigate(QuickLoginFragmentDirections.actionNavigationQuickLoginToNavigationAddAccount())


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}