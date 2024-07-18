package com.example.mywallet.features.profileModule.editProfile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.setLightStatusBars
import com.example.core.data.User
import com.example.mywallet.databinding.FragmentProfileEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private val viewModel: EditProfileViewModel by viewModels()

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        lifecycleScope.launch {
            viewModel.user.collectLatest { user ->
                user?.let {
                    setUserValues(it)
                }
            }
        }
    }

    private fun setUserValues(user: User) {
        binding.firstnameField.setText(user.firstName)
        binding.lastnameField.setText(user.lastName)
        binding.emailField.setText(user.email)
    }

    private fun initViews() {
        binding.btnFinishEdit.setOnClickListener {
            viewModel.saveChanges(getUser())
            findNavController().popBackStack()
        }
    }

    private fun getUser(): User = User(
        firstName = binding.firstnameField.text.toString(),
        lastName = binding.lastnameField.text.toString(),
        binding.emailField.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}