package com.surveasy.surveasy.presentation.main.survey

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentSurveyBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SurveyFragment : BaseFragment<FragmentSurveyBinding>(R.layout.fragment_survey) {
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun initData() = Unit

    override fun initView() = with(binding) {
        initWebView()
        vm = viewModel
        ivBack.setOnClickListener { findNavController().navigateUp() }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is SurveyEvents.NavigateToProof -> findNavController().toSurveyProof()
                    is SurveyEvents.ShowSnackBar -> showSnackBar(event.msg)
                    else -> Unit
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() = with(binding) {
        wvForm.settings.javaScriptEnabled = true

        wvForm.webViewClient = WebViewClient()
        wvForm.webChromeClient = WebChromeClient()

        repeatOnStarted {
            viewModel.uiState.collectLatest {
                wvForm.loadUrl(it.link)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (wvForm.canGoBack()) {
                    wvForm.goBack()
                } else {
                    findNavController().navigateUp()
                }
            }
        })
    }


    private fun NavController.toSurveyProof() {
        navigate(SurveyFragmentDirections.actionSurveyFragmentToSurveyProofFragment())
    }
}