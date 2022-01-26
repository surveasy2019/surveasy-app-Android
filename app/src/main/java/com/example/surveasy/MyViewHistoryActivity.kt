package com.example.surveasy

import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView

class MyViewHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewhistory)

        val historyToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewHistory)
        setSupportActionBar(historyToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        historyToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        val fragmentContainerView1 : FragmentContainerView = findViewById(R.id.fragmentContainerView1)
        val fragmentContainerView2 : FragmentContainerView = findViewById(R.id.fragmentContainerView2)

        val myViewHistoryWaitBtn : Button = findViewById(R.id.MyViewHistory_WaitBtn)
        myViewHistoryWaitBtn.setOnClickListener{
            fragmentContainerView1.visibility = View.VISIBLE
            fragmentContainerView2.visibility = View.INVISIBLE
        }

        val myViewHistoryFinBtn : Button = findViewById(R.id.MyViewHistory_FinBtn)
        myViewHistoryFinBtn.setOnClickListener{
            fragmentContainerView1.visibility = View.INVISIBLE
            fragmentContainerView2.visibility = View.VISIBLE
        }


    }
}