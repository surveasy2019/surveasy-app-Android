package com.example.surveasy.list


import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R



class SurveyItemsAdapter(val surveyList : ArrayList<SurveyItems>, val boolList : ArrayList<Boolean>) : RecyclerView.Adapter<SurveyItemsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{

            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int ) {
            holder.itemTitle.text = surveyList.get(position).title
            holder.itemDate.text = surveyList.get(position).uploadDate

        //참여한 설문 박스 색 변경
        if(boolList[position]){
            holder.itemTitle.setBackgroundColor(Color.RED)
        }else{
            holder.itemTitle.setBackgroundColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,SurveyListDetailActivity::class.java)
            intent.putExtra("link","${surveyList.get(position).link}")
            intent.putExtra("id","${surveyList.get(position).id}")
            intent.putExtra("index",position)
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