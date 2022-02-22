package com.example.surveasy.tutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityTutorialBinding
import com.example.surveasy.login.LoginActivity

class TutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Tutorials init
        val imgList = arrayOf<Int>(R.drawable.tutorial_img_1, R.drawable.tutorial_img_2, R.drawable.tutorial_img_3,
                                    R.drawable.tutorial_img_4, R.drawable.tutorial_img_5)
        val titleList = resources.getStringArray(R.array.tutorial_title)
        val contentList = resources.getStringArray(R.array.tutorial_content)

        val tutorial = Tutorial(imgList, titleList, contentList)
        binding.TutorialViewPager.adapter = TutorialViewPagerAdapter(this, tutorial)
        binding.TutorialViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL



    }
}