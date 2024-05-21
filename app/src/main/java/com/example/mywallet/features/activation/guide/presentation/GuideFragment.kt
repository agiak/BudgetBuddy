package com.example.mywallet.features.activation.guide.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.common.myutils.moveNext
import com.example.common.myutils.setLightStatusBars
import com.example.mywallet.core.presentation.ext.getNextScreenNavOptions
import com.example.mywallet.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.databinding.FragmentGuideBinding
import com.example.mywallet.databinding.FragmentRegisterBinding
import com.example.mywallet.features.activation.guide.data.GuideStep
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class GuideFragment : Fragment() {

    private val viewModel: GuideViewModel by viewModels()

    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!

    private val guideAdapter = GuideAdapter(steps = GuideStep.entries)
    private var viewPagerPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
    }

    private fun initViews() {
        binding.btnNext.setOnClickListener { navigateToNextStep() }

        binding.viewPager.apply {
            adapter = guideAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewPagerPosition = position
                    if (isTheLastStep()) {
                        binding.btnNext.text = "Start"
                    } else {
                        binding.btnNext.text = "Next"
                    }
                }
            })
        }
    }

    private fun navigateToNextStep() {
        when {
            isTheLastStep() -> {
                viewModel.setGuideAsDisplayed()
                navigateToRegisterScreen()
            }
            else -> binding.viewPager.moveNext()
        }
    }

    private fun isTheLastStep() = viewPagerPosition == GuideStep.entries.size-1

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun GuideFragment.navigateToRegisterScreen() =
   navigateToNextScreen(GuideFragmentDirections.actionNavigationQuideToNavigationRegister())