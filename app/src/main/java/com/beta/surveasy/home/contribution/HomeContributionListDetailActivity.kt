package com.beta.surveasy.home.contribution

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.beta.surveasy.R
import com.beta.surveasy.databinding.ActivityHomecontributionlistdetailBinding

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

        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val journal = intent.getStringExtra("journal")
        val source = intent.getStringExtra("source")
        val institute = intent.getStringExtra("institute")
        val img = intent.getStringExtra("img")
        val content = intent.getStringExtra("content")

        val imgUri : Uri = img!!.toUri()

        if(journal != null) {
            binding.HomeContributionListDetailJournal.visibility = View.VISIBLE
        }

        Glide.with(this)
            .load(img)
            .placeholder(R.drawable.app_loading_img)
            .into(binding.HomeContributionListDetailImg)


        binding.HomeContributionListDetailContent.text = content
        binding.HomeContributionListDetailTitle.text = title
        binding.HomeContributionListDetailJournal.text = journal
        binding.HomeContributionListDetailSource.text = source
        binding.HomeContributionListDetailInstitute.text = institute




    }


}