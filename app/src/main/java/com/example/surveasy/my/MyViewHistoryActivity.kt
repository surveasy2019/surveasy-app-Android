package com.example.surveasy.my

import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import com.example.surveasy.databinding.ActivityMyviewhistoryBinding

class MyViewHistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyviewhistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewhistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewHistory)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewHistory.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.MyViewHistoryWaitBtn.setOnClickListener{
            binding.fragmentContainerView1.visibility = View.VISIBLE
            binding.fragmentContainerView2.visibility = View.INVISIBLE
        }

        binding.MyViewHistoryFinBtn.setOnClickListener{
            binding.fragmentContainerView1.visibility = View.INVISIBLE
            binding.fragmentContainerView2.visibility = View.VISIBLE
        }


    }
}