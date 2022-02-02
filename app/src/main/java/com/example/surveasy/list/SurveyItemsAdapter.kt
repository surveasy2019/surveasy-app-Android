package com.example.surveasy.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R


class SurveyItemsAdapter(val surveyList : ArrayList<SurveyItems>) : RecyclerView.Adapter<SurveyItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                Toast.makeText(parent.context,"clicked!",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = surveyList.get(position).title
        holder.itemDate.text = surveyList.get(position).date.toString()

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,SurveyListDetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }

    override fun getItemCount(): Int {
        return surveyList.size
    }


    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemTitle : TextView = itemView.findViewById(R.id.ListItem_Title)
        val itemDate : TextView = itemView.findViewById(R.id.ListItem_date)
    }
}