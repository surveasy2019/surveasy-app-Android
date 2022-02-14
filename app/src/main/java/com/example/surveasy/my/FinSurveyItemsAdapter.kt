package com.example.surveasy.my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.list.UserSurveyItem

class FinSurveyItemsAdapter(val finList : ArrayList<UserSurveyItem>) : RecyclerView.Adapter<FinSurveyItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FinSurveyItemsAdapter.CustomViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item,parent,false)

        return CustomViewHolder(view)
    }



    override fun onBindViewHolder(holder: FinSurveyItemsAdapter.CustomViewHolder, position: Int) {
        holder.itemTitle.text = finList.get(position).title
        holder.itemDate.text = finList.get(position).responseDate
    }

    override fun getItemCount(): Int {
        return finList.size
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle : TextView = itemView.findViewById(R.id.HistoryItem_Title)
        val itemDate : TextView = itemView.findViewById(R.id.HistoryItem_date)
    }

}