package com.surveasy.surveasy.home.Opinion

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surveasy.surveasy.R

class AnswerItemAdapter(context: Context, val urlList: List<String>) : RecyclerView.Adapter<AnswerItemAdapter.CustomViewHolder>() {
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemAdapter.CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_answer,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnswerItemAdapter.CustomViewHolder, position: Int) {
        Glide.with(context).load(urlList[position]).into(holder.img)
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.AnswerItem_Img)
    }


}