package com.surveasy.surveasy.presentation.introduce

import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityFirstIntroduceBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.introduce.model.UiFirstIntroduceData

class FirstIntroduceActivity :
    BaseActivity<ActivityFirstIntroduceBinding>(ActivityFirstIntroduceBinding::inflate) {


    override fun initView() {
        val imgList = arrayOf(
            R.drawable.tutorial_img_1, R.drawable.tutorial_img_2, R.drawable.tutorial_img_3,
            R.drawable.tutorial_img_4, R.drawable.tutorial_img_5
        ).toList()
        val titleList = resources.getStringArray(R.array.tutorial_title).toList()
        val contentList = resources.getStringArray(R.array.tutorial_content).toList()
        val adapter = FirstIntroduceAdapter(UiFirstIntroduceData(imgList, titleList, contentList)) {
            navigateToLogin()
        }
        binding.vpIntroduce.adapter = adapter

        binding.ciIndicator.setViewPager2(binding.vpIntroduce)

    }

    override fun initEventObserver() {

    }

    override fun initData() {

    }

    private fun navigateToLogin() {
        showToastMessage("start")
    }
}