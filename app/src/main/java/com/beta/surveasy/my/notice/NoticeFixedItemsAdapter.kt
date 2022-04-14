package com.beta.surveasy.my.notice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.beta.surveasy.R

class NoticeFixedItemsAdapter(val noticeList : ArrayList<NoticeItems>)
    : RecyclerView.Adapter<NoticeFixedItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_notice_fixed,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val notice : NoticeItems = noticeList[position]
        holder.noticeItemTitle.text = notice.title
        holder.noticeItemDate.text = notice.date
        holder.noticeItemContent.text = notice.content

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView!!.context, MyViewNoticeListDetailActivity::class.java)
            intent.putExtra("title", holder.noticeItemTitle.text)
            intent.putExtra("date", holder.noticeItemDate.text)
            intent.putExtra("content", holder.noticeItemContent.text)
            intent.putExtra("fixed", true)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noticeItemTitle : TextView = itemView.findViewById(R.id.NoticeFixedItem_Title)
        val noticeItemDate: TextView = itemView.findViewById(R.id.NoticeFixedItem_Date)
        val noticeItemContent : TextView = itemView.findViewById(R.id.NoticeFixedItem_Content)

    }


}