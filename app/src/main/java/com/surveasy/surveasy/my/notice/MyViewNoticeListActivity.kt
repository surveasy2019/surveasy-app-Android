package com.surveasy.surveasy.my.notice

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.surveasy.surveasy.databinding.ActivityMyviewnoticelistBinding
import com.google.firebase.firestore.*
import com.surveasy.surveasy.my.notice.noticeRoom.Notice
import com.surveasy.surveasy.my.notice.noticeRoom.NoticeDatabase

class MyViewNoticeListActivity : AppCompatActivity() {

    private lateinit var noticeRecyclerView_Fixed: RecyclerView
    private lateinit var noticeRecyclerView: RecyclerView
    private lateinit var noticeList_fixed : ArrayList<NoticeItems>
    private lateinit var noticeList : ArrayList<NoticeItems>

    private lateinit var db : FirebaseFirestore
    private lateinit var noticeDB : NoticeDatabase

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


        // Initiate Room DB
        noticeDB = Room.databaseBuilder(
            this,
            NoticeDatabase::class.java, "NoticeDatabase"
        ).allowMainThreadQueries().build()



        val noticeDiff = intent.getIntExtra("noticeDiff", 0)
        val notice_room = intent.getIntExtra("notice_room", 0)

        fetchNoticeData(noticeDiff, notice_room)

    }

    private fun fetchNoticeData(noticeDiff: Int, notice_room: Int) {
        noticeRecyclerView_Fixed = binding.recyclerNoticeContainerFixed
        noticeRecyclerView_Fixed.setHasFixedSize(true)
        noticeList_fixed = arrayListOf()


        noticeRecyclerView = binding.recyclerNoticeContainer
        noticeRecyclerView.setHasFixedSize(true)
        noticeList = arrayListOf()


        db = FirebaseFirestore.getInstance()
        db.collection("AppNotice").orderBy("id", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                noticeList_fixed.clear()
                noticeList.clear()

                for (document in result) {

                    // [Case 1] fixed
                    if (document["fixed"] == true) {
                        if (Integer.parseInt(document["id"].toString()) > notice_room) {
                            var item_fixed: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = false
                            )
                            noticeList_fixed.add(item_fixed)
                            Log.d(ContentValues.TAG, "___________________1")
                        } else {
                            var item_fixed: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = true
                            )
                            noticeList_fixed.add(item_fixed)
                            Log.d(ContentValues.TAG, "___________________2")
                        }

                        noticeRecyclerView_Fixed.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        noticeRecyclerView_Fixed.adapter = NoticeFixedItemsAdapter(noticeList_fixed)
                    }

                    // [Case 2] not fixed
                    else {
                        if (Integer.parseInt(document["id"].toString()) > notice_room) {
                            var item: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = false
                            )
                            noticeList.add(item)
                            Log.d(ContentValues.TAG, "___________________3")
                        } else {
                            var item: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = true
                            )
                            noticeList.add(item)
                            Log.d(ContentValues.TAG, "___________________4")
                        }

                        noticeRecyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        noticeRecyclerView.adapter = NoticeItemsAdapter(noticeList)
                    }


                    // Local DB에 new notice 추가
                    if (Integer.parseInt(document["id"].toString()) > notice_room) {
                        val notice: Notice =
                            Notice(Integer.parseInt(document["id"].toString()), true)
                        noticeDB.noticeDao().insert(notice)
                    }

                }

            }


    }

}


