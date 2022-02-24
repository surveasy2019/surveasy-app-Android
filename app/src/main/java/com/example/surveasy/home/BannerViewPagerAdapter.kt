package com.example.surveasy.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.surveasy.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.net.URLConnection


class BannerViewPagerAdapter(context: Context, bannerList: ArrayList<String>): RecyclerView.Adapter<BannerViewPagerAdapter.PagerViewHolder>() {
    var bannerList = bannerList
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewPagerAdapter.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewPagerAdapter.PagerViewHolder, position: Int) {
        Glide.with(context).load(bannerList[position]).into(holder.banner)

    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val banner = itemView.findViewById<ImageView>(R.id.Banner_Img)

    }



}