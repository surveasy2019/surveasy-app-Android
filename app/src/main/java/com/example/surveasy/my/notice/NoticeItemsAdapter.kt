package com.example.surveasy.my.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R

class NoticeItemsAdapter(val noticeList : ArrayList<NoticeItems>)
    : RecyclerView.Adapter<NoticeItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notice_item,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val notice : NoticeItems = noticeList[position]
        holder.noticeItemTitle.text = notice.title
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noticeItemTitle : TextView = itemView.findViewById(R.id.NoticeItem_Title)
    }


}