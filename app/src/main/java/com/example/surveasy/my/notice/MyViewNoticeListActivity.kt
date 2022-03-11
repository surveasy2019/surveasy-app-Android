package com.example.surveasy.my.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.databinding.ActivityMyviewnoticelistBinding
import com.google.firebase.firestore.*

class MyViewNoticeListActivity : AppCompatActivity() {

    private lateinit var noticeRecyclerView_Fixed: RecyclerView
    private lateinit var noticeRecyclerView: RecyclerView
    private lateinit var noticeList_fixed : ArrayList<NoticeItems>
    private lateinit var noticeList : ArrayList<NoticeItems>
    private lateinit var db : FirebaseFirestore



    private lateinit var binding : ActivityMyviewnoticelistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyviewnoticelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarMyViewNoticeList)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewNoticeList.setNavigationOnClickListener {
            onBackPressed()
        }

        fetchNoticeData()

    }

    private fun fetchNoticeData() {
        noticeRecyclerView_Fixed = binding.recyclerNoticeContainerFixed
        noticeRecyclerView_Fixed.setHasFixedSize(true)
        noticeList_fixed = arrayListOf()


        noticeRecyclerView = binding.recyclerNoticeContainer
        noticeRecyclerView.setHasFixedSize(true)
        noticeList = arrayListOf()


        db = FirebaseFirestore.getInstance()
        db.collection("AppNotice")
            .get()
            .addOnSuccessListener { result ->
                noticeList_fixed.clear()
                noticeList.clear()

                for(document in result) {
                    if(document["fixed"] == true) {
                        var item_fixed : NoticeItems = NoticeItems(document["title"] as String,
                            document["date"] as String, document["content"] as String, document["fixed"] as Boolean)
                        noticeList_fixed.add(item_fixed)
                        noticeRecyclerView_Fixed.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                        noticeRecyclerView_Fixed.adapter = NoticeFixedItemsAdapter(noticeList_fixed)
                    }
                    else {
                        var item : NoticeItems = NoticeItems(document["title"] as String,
                            document["date"] as String, document["content"] as String, document["fixed"] as Boolean)
                        noticeList.add(item)
                        noticeRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                        noticeRecyclerView.adapter = NoticeItemsAdapter(noticeList)
                    }

                }

            }
    }



}


