package com.example.surveasy.list

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.surveasy.databinding.ActivitySurveylistdetailresponseBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class SurveyListDetailResponseActivity : AppCompatActivity() {

    val storage = Firebase.storage

    var pickImageFromAlbum = 0
    var uriPhoto : Uri? = null

    private lateinit var binding: ActivitySurveylistdetailresponseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveylistdetailresponseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarSurveyListDetailResponse)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarSurveyListDetailResponse.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.SurveyListDetailResponseBtn.setOnClickListener {
            if(checkPermission()){
                var photoPick = Intent(Intent.ACTION_PICK)
                photoPick.type = "image/*"
                startActivityForResult(photoPick,pickImageFromAlbum)

            }else{
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }

        }
        binding.SurveyListDetailResponseDoneBtn.setOnClickListener {
            uploadStorage(binding.proofImageView)
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
        val imgName = "pannelID__"+timestamp
        val storageRef = storage.reference.child("proof").child(imgName)

        storageRef.putFile(uriPhoto!!)?.addOnSuccessListener {
            Toast.makeText(view.context, "ImageUpload", Toast.LENGTH_LONG).show()
        }


    }


    }
