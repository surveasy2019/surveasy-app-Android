package com.surveasy.surveasy.home.Opinion

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHomeOpinionAnswerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeOpinionAnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeOpinionAnswerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeOpinionAnswerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //ToolBar
        setSupportActionBar(binding.ToolbarHomeOpinionAnswerDetail)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarHomeOpinionAnswerDetail.setNavigationOnClickListener {
            onBackPressed()
        }


        val content1 = intent.getStringExtra("content1")
        val content2 = intent.getStringExtra("content2")
        val content3 = intent.getStringExtra("content3")
        fetchAnswerImg()
        binding.HomeOpinionDetailAnswerContent1.text = content1.toString()
        binding.HomeOpinionDetailAnswerContent2.text = content2.toString()

    }
    private fun fetchAnswerImg() {
        val id = intent.getIntExtra("id", 1)
        val storage : FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef : StorageReference = storage.reference.child("AppOpinionAnswerImage").child(id.toString())
        val listAllTask: Task<ListResult> = storageRef.listAll()

        val file1 : StorageReference = storageRef.child("001.png")
        val file2 : StorageReference = storageRef.child("002.png")

        Glide.with(this).load(R.raw.app_loading).into(binding.HomeOpinionAnswerImg1)


        file1.downloadUrl.addOnSuccessListener { item ->
            file2.downloadUrl.addOnSuccessListener { item1 ->
                Glide.with(this).load(item1).into(binding.HomeOpinionAnswerImg2)
                Glide.with(this).load(item).into(binding.HomeOpinionAnswerImg1)
            }

        }




    }

}