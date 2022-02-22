package com.example.surveasy.tutorial

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surveasy.R
import com.example.surveasy.login.LoginActivity

class TutorialViewPagerAdapter(context: Context, tutorial : Tutorial)
    : RecyclerView.Adapter<TutorialViewPagerAdapter.PagerViewHolder>() {
    val context = context
    val imgList = tutorial.imgList
    val titleList = tutorial.titleList
    val contentList = tutorial.contentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewPagerAdapter.PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tutorial_item, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorialViewPagerAdapter.PagerViewHolder, position: Int) {
        holder.img.setImageResource(imgList[position])
        holder.title.text = titleList[position]
        holder.content.text = contentList[position]
        if(position == imgList.size-1) {
            holder.startBtn.visibility = View.VISIBLE
            holder.startBtn.setOnClickListener{
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.Tutorial_ImageView)
        val title = itemView.findViewById<TextView>(R.id.Tutorial_Title)
        val content = itemView.findViewById<TextView>(R.id.Tutorial_Content)
        val startBtn = itemView.findViewById<Button>(R.id.Tutorial_StartBtn)
    }
}