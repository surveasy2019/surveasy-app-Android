package com.example.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.databinding.ActivityMyviewnoticelistBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Document

class MyViewNoticeListActivity : AppCompatActivity() {

    private lateinit var noticeRecyclerView: RecyclerView
    private lateinit var noticeList : ArrayList<NoticeItems>
    private lateinit var db : FirebaseFirestore


    private lateinit var binding : ActivityMyviewnoticelistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyviewnoticelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewNoticeList)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

//        binding.MyViewNoticeListItemTitle.setOnClickListener {
//            val intent = Intent(this,MyViewNoticeListDetailActivity::class.java)
//            startActivity(intent)
//        }
//        binding.MyViewNoticeListItemPinTitle.setOnClickListener {
//            val intent = Intent(this,MyViewNoticeListDetailActivity::class.java)
//            startActivity(intent)
//        }

        binding.ToolbarMyViewNoticeList.setNavigationOnClickListener {
            onBackPressed()
        }

        fetchNoticeData()

    }

    private fun fetchNoticeData() {
        noticeRecyclerView = binding.recyclerNoticeContainer
        noticeRecyclerView.setHasFixedSize(true)

        noticeList = arrayListOf()


        db = FirebaseFirestore.getInstance()
        db.collection("AppNotice")
            .get()
            .addOnSuccessListener { result ->
                noticeList.clear()
                for(document in result) {
                    var item : NoticeItems = NoticeItems(document["title"] as String, document["date"] as String)
                    noticeList.add(item)
                    noticeRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                    noticeRecyclerView.adapter = NoticeItemsAdapter(noticeList)
                }

            }
    }



}


