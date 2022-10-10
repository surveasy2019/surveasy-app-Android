package com.surveasy.surveasy.my.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surveasy.surveasy.MainActivity
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMyViewHistoryUpdateFinDialogBinding

class MyViewHistoryUpdateFinDialogActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyViewHistoryUpdateFinDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyViewHistoryUpdateFinDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myHistoryUpdateFinBtn.setOnClickListener {

            finish()
        }
    }
}