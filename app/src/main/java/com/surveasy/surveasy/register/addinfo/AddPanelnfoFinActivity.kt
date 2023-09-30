package com.surveasy.surveasy.register.addinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.surveasy.surveasy.R

class AddPanelnfoFinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registerfin)
        val btn : Button = findViewById(R.id.RegisterFinFragment_Btn)

        btn.setOnClickListener {

        }
    }
}