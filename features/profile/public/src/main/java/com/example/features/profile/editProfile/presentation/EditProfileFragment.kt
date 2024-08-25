package com.example.features.profile.editProfile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.hide
import com.example.common.myutils.loadCircle
import com.example.common.myutils.setLightStatusBars
import com.example.core.data.User
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.onBack
import com.example.features.profile.R
import com.example.features.profile.databinding.FragmentProfileEditBinding
import com.example.features.profile.impl.editProfile.data.EditUserEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private val viewModel: EditProfileViewModel by viewModels()

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                requireContext().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                sentEvent(EditUserEvent.IconChanged(it.toString()))
                binding.profileImage.loadCircle(it)
            }
        }

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
        initToolbar()
        initSubscriptions()
    }

    private fun initToolbar() {
        binding.toolbar.screenTitle.text = getString(R.string.screen_title)
        binding.toolbar.backButton.setOnClickListener { onBack() }
        binding.toolbar.optionsButton.hide()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
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
        user.icon?.let { binding.profileImage.loadCircle(it) }
    }

    private fun initViews() {
        binding.btnFinishEdit.setOnClickListener {
            sentEvent(EditUserEvent.OnSave)
            onBack()
        }

        binding.lastnameField.doAfterTextChanged { text ->
            sentEvent(EditUserEvent.LastNameChanged(text.toString()))
        }

        binding.firstnameField.doAfterTextChanged { text ->
            sentEvent(EditUserEvent.FirstNameChanged(text.toString()))
        }

        binding.emailField.doAfterTextChanged { text ->
            sentEvent(EditUserEvent.EmailChanged(text.toString()))
        }

        binding.profileImage.setOnClickListener { openGallery() }
        binding.btnEdit.setOnClickListener { openGallery() }
    }

    private fun sentEvent(userEvent: EditUserEvent) {
        viewModel.onEvent(userEvent)
    }

    private fun openGallery() {
        imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
