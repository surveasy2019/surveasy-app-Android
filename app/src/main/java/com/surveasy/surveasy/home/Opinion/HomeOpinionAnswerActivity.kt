package com.surveasy.surveasy.home.Opinion

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHomeOpinionAnswerBinding

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


        listAllTask.addOnSuccessListener { result ->
            val items : List<StorageReference> = result.items
            val itemNum : Int = result.items.size

            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    binding.HomeOpinionAnswerImg1.setImageURI(it!!)
                    binding.HomeOpinionAnswerImg2.setImageURI(it!!)
                    Log.d(TAG, "UUUUUUUU--${it}")
                }

            }
        }
    }

}