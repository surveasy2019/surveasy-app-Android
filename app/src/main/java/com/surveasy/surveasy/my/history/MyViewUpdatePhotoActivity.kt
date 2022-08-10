package com.surveasy.surveasy.my.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHomeOpinionAnswerBinding
import com.surveasy.surveasy.databinding.ActivityMyViewUpdatePhotoBinding

class MyViewUpdatePhotoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyViewUpdatePhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyViewUpdatePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val filePath = intent.getStringExtra("filePath")
        val filePath = "surveytips2image(1).jpeg"
        val id = intent.getIntExtra("id",0)
        fetchAnswerImg()
    }

    private fun fetchAnswerImg() {

        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("historyTest")


        val file1: StorageReference = storageRef.child("surveytips2image(1).jpeg")


        Glide.with(this).load(R.raw.app_loading).into(binding.MyViewHistoryLastPhoto)


        file1.downloadUrl.addOnSuccessListener { item ->

            Glide.with(this).load(item).into(binding.MyViewHistoryLastPhoto)


        }
    }
}