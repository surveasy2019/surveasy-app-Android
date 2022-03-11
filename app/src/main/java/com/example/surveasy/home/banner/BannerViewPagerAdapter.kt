package com.example.surveasy.home.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surveasy.R


class BannerViewPagerAdapter(context: Context, bannerList: ArrayList<String>): RecyclerView.Adapter<BannerViewPagerAdapter.PagerViewHolder>() {
    var bannerList = bannerList
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(context).load(bannerList[position]).into(holder.banner)

    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val banner = itemView.findViewById<ImageView>(R.id.Banner_Img)

    }



}