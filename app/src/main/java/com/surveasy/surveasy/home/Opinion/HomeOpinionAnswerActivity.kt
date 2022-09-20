package com.surveasy.surveasy.home.Opinion

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHomeOpinionAnswerBinding
import com.surveasy.surveasy.home.Opinion.AnswerItemAdapter
import kotlinx.coroutines.*
import java.security.DigestInputStream

class HomeOpinionAnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeOpinionAnswerBinding
    val urlList = ArrayList<String>()
    var itemNum : Int = 0

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

        val id = intent.getIntExtra("id", 1)
        val content1 = intent.getStringExtra("content1")
        val content2 = intent.getStringExtra("content2")
        val content3 = intent.getStringExtra("content3")
        val itemNum = intent.getIntExtra("itemNum", 0)

        binding.HomeOpinionDetailAnswerContent1.text = content1.toString()
        binding.HomeOpinionDetailAnswerContent2.text = content2.toString()


        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("AppOpinionAnswerImage").child(id.toString())


        // itemNum만큼 이미지 url 불러와서 urlList에 저장 -> Adapter 호출
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                for(i : Int in 1..itemNum) {
                    var fileName :  String

                    if(itemNum < 10)  fileName = "00" + i.toString() + ".png"
                    else fileName = "0" + i.toString() + ".png"

                    var fileRef : StorageReference = storageRef.child(fileName)
                    fileRef.downloadUrl.addOnSuccessListener { result ->
                        urlList.add(result.toString())
                        if(urlList.size == itemNum) {
                            binding.HomeOpinionAnswerLoading.visibility = View.GONE
                            val urlList_sorted = urlList.sorted()
                            binding.HomeOpinionAnswerRV.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
                            binding.HomeOpinionAnswerRV.adapter = AnswerItemAdapter(baseContext, urlList_sorted)  // 이미지를 url 기준으로 정렬
                        }
                    }
                }
            }.await()

        }

    }

}


