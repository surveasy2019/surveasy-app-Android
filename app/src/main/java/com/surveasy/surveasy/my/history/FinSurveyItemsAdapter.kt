package com.surveasy.surveasy.my.history

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.surveasy.surveasy.R
import com.surveasy.surveasy.list.UserSurveyItem

class FinSurveyItemsAdapter(val finList : ArrayList<UserSurveyItem>) : RecyclerView.Adapter<FinSurveyItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)

        return CustomViewHolder(view)
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = finList.get(position).title
        holder.itemDate.text = finList.get(position).responseDate
        holder.itemReward.text = finList.get(position).reward.toString() + "원"
        holder.itemBtn.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return finList.size
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle : TextView = itemView.findViewById(R.id.HistoryItem_Title)
        val itemDate : TextView = itemView.findViewById(R.id.HistoryItem_date)
        val itemReward : TextView = itemView.findViewById(R.id.HistoryItem_Reward)
        val itemBtn : ImageView = itemView.findViewById(R.id.historyItem_picBtn)
        //val itemBtn : Button = itemView.findViewById(R.id.MyView_history_photo)
    }

}