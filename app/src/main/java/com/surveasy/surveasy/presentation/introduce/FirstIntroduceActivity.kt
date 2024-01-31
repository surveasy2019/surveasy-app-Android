package com.surveasy.surveasy.presentation.introduce

import android.content.Intent
import androidx.activity.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityFirstIntroduceBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.intro.IntroActivity
import com.surveasy.surveasy.presentation.introduce.model.UiFirstIntroduceData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstIntroduceActivity :
    BaseActivity<ActivityFirstIntroduceBinding>(ActivityFirstIntroduceBinding::inflate) {

    private val viewModel: FirstIntroduceViewModel by viewModels()

    override fun initView() {
        val imgList = arrayOf(
            R.drawable.tutorial_img_1,
            R.drawable.tutorial_img_2,
            R.drawable.tutorial_img_4,
            R.drawable.tutorial_img_5
        ).toList()
        val titleList = resources.getStringArray(R.array.tutorial_title).toList()
        val contentList = resources.getStringArray(R.array.tutorial_content).toList()
        val adapter = FirstIntroduceAdapter(UiFirstIntroduceData(imgList, titleList, contentList)) {
            viewModel.navigateToLogin()
        }
        binding.vpIntroduce.adapter = adapter

        binding.ciIndicator.setViewPager2(binding.vpIntroduce)

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is FirstIntroduceEvents.NavigateToLogin -> navigateToIntro()
                }
            }
        }
    }

    private fun navigateToIntro() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun initData() = Unit

}