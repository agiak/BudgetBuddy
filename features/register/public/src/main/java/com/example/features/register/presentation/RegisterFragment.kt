package com.example.features.register.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.myutils.loadCircle
import com.example.common.myutils.setLightStatusBars
import com.example.core.data.User
import com.example.core.data.screens.AuthorizationFlow
import com.example.core.presentation.createAccountScreen
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.features.register.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var hasAccounts = false
    private var selectedImage: String? = null

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                requireContext().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                selectedImage = it.toString()
                binding.profileImage.loadCircle(it)
            }
        }

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

        binding.profileImage.setOnClickListener { openGallery() }
    }

    private fun openGallery() {
        imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
            icon = selectedImage
        )

    private fun navigateToNextScreen() {
        when (hasAccounts) {
            false -> navigateToAddAccountScreen()
            else -> (requireActivity() as AuthorizationFlow).startMainFlow()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun RegisterFragment.navigateToAddAccountScreen() =
    navigateToNextScreen(createAccountScreen)
