package com.surveasy.surveasy.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import com.surveasy.surveasy.databinding.ActivitySplashBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.intro.IntroActivity
import com.surveasy.surveasy.presentation.introduce.FirstIntroduceActivity
import com.surveasy.surveasy.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun initView() {
        binding.vm = viewModel
    }

    override fun initData() = Unit

    override fun initEventObserver() {
        repeatOnStarted {
            delay(2000)
            viewModel.chooseNextPage()
        }
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is SplashUiEvent.NavigateToTutorial -> navigateToTutorial()
                    is SplashUiEvent.NavigateToIntro -> navigateToIntro()
                    is SplashUiEvent.NavigateToMain -> navigateToMain()
                }
            }
        }
    }

    private fun navigateToTutorial() {
        val intent = Intent(this, FirstIntroduceActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToIntro() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }
}