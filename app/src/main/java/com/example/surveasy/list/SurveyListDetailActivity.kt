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

    val storage = Firebase.storage

    var pickImageFromAlbum = 0
    var uriPhoto : Uri? = null

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



//        binding.SurveyListDetailBtn.setOnClickListener {
//            val intent = Intent(this, SurveyListDetailResponseActivity::class.java)
//            startActivity(intent)
//
//        }
        setSupportActionBar(binding.ToolbarProof)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarProof.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.toolbarUpload.setOnClickListener {
            if(checkPermission()){
                var photoPick = Intent(Intent.ACTION_PICK)
                photoPick.type = "image/*"
                startActivityForResult(photoPick,pickImageFromAlbum)

            }else{
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }

        }


        binding.toolbarSend.setOnClickListener {
            uploadStorage(binding.proofImageView)
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

    private fun checkPermission() : Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){
                uriPhoto = data?.data
                binding.proofImageView.setImageURI(uriPhoto)

            }
        }
    }

    private fun uploadStorage(view: View){
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmm").format(Date())
        val title : String= intent.getStringExtra("title")!!
        val imgName = title+"__"+timestamp
        val storageRef = storage.reference.child(title).child(imgName)

        storageRef.putFile(uriPhoto!!)?.addOnSuccessListener {
            Toast.makeText(view.context, "ImageUpload", Toast.LENGTH_LONG).show()
        }


    }







    }

