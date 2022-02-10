package com.example.surveasy.list

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivitySurveylistdetailBinding
import com.example.surveasy.home.HomeFragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*


class SurveyListDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySurveylistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySurveylistdetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var webView : WebView = binding.surveyWebView

        val apply = webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        val url : String = intent.getStringExtra("url")!!

        webView.loadUrl(url)


        val title : String = intent.getStringExtra("title")!!
        val index : Int = intent.getIntExtra("index",0)!!
        Log.d(TAG,"####${title}, ${index}")


        binding.toolbarUpload.setOnClickListener {
            val intent = Intent(this, SurveyProofDialogActivity::class.java)
            val title = intent.putExtra("title",title)
            val index = intent.putExtra("index",index)
            startActivityForResult(intent,101)
            

       }
        setSupportActionBar(binding.ToolbarProof)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarProof.setNavigationOnClickListener {
            onBackPressed()
        }






    }


    override fun onBackPressed() {
        var webView : WebView = binding.surveyWebView
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            finish()
        }
    }













    }

