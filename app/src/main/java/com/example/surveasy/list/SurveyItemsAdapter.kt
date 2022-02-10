package com.example.surveasy.list

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyItemsAdapter(val surveyList : ArrayList<SurveyItems>) : RecyclerView.Adapter<SurveyItemsAdapter.CustomViewHolder>() {

    val db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{

            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

            holder.itemTitle.text = surveyList.get(position).title
            holder.itemDate.text = surveyList.get(position).date

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,SurveyListDetailActivity::class.java)
            intent.putExtra("url","${surveyList.get(position).url}")
            intent.putExtra("title","${surveyList.get(position).title}")
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