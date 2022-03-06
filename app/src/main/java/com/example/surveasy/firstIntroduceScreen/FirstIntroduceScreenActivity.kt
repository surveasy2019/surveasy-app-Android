package com.example.surveasy.firstIntroduceScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityFirstintroducescreenBinding

class FirstIntroduceScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstintroducescreenBinding
    var last_page : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstintroducescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // First Introduce Screen init
        val imgList = arrayOf<Int>(R.drawable.tutorial_img_1, R.drawable.tutorial_img_2, R.drawable.tutorial_img_3,
                                    R.drawable.tutorial_img_4, R.drawable.tutorial_img_5)
        val titleList = resources.getStringArray(R.array.tutorial_title)
        val contentList = resources.getStringArray(R.array.tutorial_content)


        val firstIntroduceScreen = FirstIntroduceScreen(imgList, titleList, contentList)
        // binding.FirstIntroduceScreenViewPager.offscreenPageLimit = imgList.size
        binding.FirstIntroduceScreenViewPager.adapter = FirstIntroduceScreenViewPagerAdapter(this, binding.root, firstIntroduceScreen)
        binding.FirstIntroduceScreenViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val springDotIndicator = binding.springDotsIndicator
        springDotIndicator.setViewPager2(binding.FirstIntroduceScreenViewPager)


    }
}