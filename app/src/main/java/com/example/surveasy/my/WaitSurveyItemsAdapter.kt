package com.example.surveasy.my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.list.UserSurveyItem

class WaitSurveyItemsAdapter(val waitList : ArrayList<UserSurveyItem>) : RecyclerView.Adapter<WaitSurveyItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaitSurveyItemsAdapter.CustomViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item,parent,false)

        return CustomViewHolder(view)
    }



    override fun onBindViewHolder(holder: WaitSurveyItemsAdapter.CustomViewHolder, position: Int) {
        holder.itemTitle.text = waitList.get(position).title
        holder.itemDate.text = waitList.get(position).responseDate
    }

    override fun getItemCount(): Int {
        return waitList.size
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle : TextView = itemView.findViewById(R.id.HistoryItem_Title)
        val itemDate : TextView = itemView.findViewById(R.id.HistoryItem_date)
    }

}