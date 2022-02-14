package com.example.surveasy.list

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyItemsAdapter(val surveyList : ArrayList<SurveyItems>, val boolList : ArrayList<Boolean>) : RecyclerView.Adapter<SurveyItemsAdapter.CustomViewHolder>() {

    val db = Firebase.firestore

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