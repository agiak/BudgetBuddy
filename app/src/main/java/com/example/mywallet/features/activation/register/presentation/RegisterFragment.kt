package com.example.mywallet.features.activation.register.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.myutils.setLightStatusBars
import com.example.core.data.User
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.core.presentation.openMainFlow
import com.example.mywallet.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var hasAccounts = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initViews() {
        binding.btnRegister.setOnClickListener {
            viewModel.register(getUser())
            navigateToNextScreen()
        }
    }

    private fun initSubscriptions() {
        lifecycleScope.launch {
            viewModel.hasAccount.collectLatest {
                hasAccounts = it
            }
        }
    }

    private fun getUser(): User =
        User(
            firstName = binding.firstnameField.text.toString(),
            lastName = binding.lastnameField.text.toString(),
            email = binding.emailField.text.toString(),
        )

    private fun navigateToNextScreen() {
        when (hasAccounts) {
            false -> navigateToAddAccountScreen()
            else -> requireContext().openMainFlow()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun RegisterFragment.navigateToAddAccountScreen() =
    navigateToNextScreen(RegisterFragmentDirections.actionNavigationRegisterToNavigationAddAccount())
