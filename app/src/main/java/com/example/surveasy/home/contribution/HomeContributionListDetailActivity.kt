package com.example.surveasy.home.contribution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.example.surveasy.databinding.ActivityHomecontributionlistdetailBinding
import com.example.surveasy.databinding.ActivityMyviewnoticelistdetailBinding

class HomeContributionListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomecontributionlistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomecontributionlistdetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarHomeContributionListDetail)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarHomeContributionListDetail.setNavigationOnClickListener {
            onBackPressed()
        }


        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val institute = intent.getStringExtra("institute")
        val content = intent.getStringArrayListExtra("content")
        val img = intent.getStringExtra("img")


        var contentNum = 0

        if(content!![1] == "")  contentNum = 1
        else if(content!![2] == "")  contentNum = 2
        else if(content!![3] == "")  contentNum = 3
        else contentNum = 4

        when(contentNum) {
            1 -> content1(content!![0])
            2 -> {
                content1(content!![0])
                content2(content!![1])
            }
            3 -> {
                content1(content!![0])
                content2(content!![1])
                content3(content!![2])
            }
            4 -> {
                content1(content!![0])
                content2(content!![1])
                content3(content!![2])
                content4(content!![3])
            }
        }




        binding.HomeContributionListDetailTitle.text = title
        binding.HomeContributionListDetailInstitute.text = institute
//        binding.HomeContributionListDetailImg.setImageURI(img!!.toUri())







    }

    private fun content1(content: String) {
        binding.HomeContributionListDetailContent1.text = content
    }

    private fun content2(content: String) {
        binding.HomeContributionListDetailContent2.visibility = View.VISIBLE
        binding.HomeContributionListDetailContent2.text = content
    }

    private fun content3(content: String) {
        binding.HomeContributionListDetailContent3.visibility = View.VISIBLE
        binding.HomeContributionListDetailContent3.text = content
    }

    private fun content4(content: String) {
        binding.HomeContributionListDetailContent4.visibility = View.VISIBLE
        binding.HomeContributionListDetailContent4.text = content
    }
}