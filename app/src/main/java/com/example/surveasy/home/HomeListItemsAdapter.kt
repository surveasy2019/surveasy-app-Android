package com.example.surveasy.home

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.list.NoticeToPanelDialogActivity
import com.example.surveasy.list.SurveyItems
import com.example.surveasy.list.SurveyListDetailActivity
import com.example.surveasy.list.SurveyListDetailDialogActivity


class HomeListItemsAdapter(val homeList: ArrayList<SurveyItems>) : RecyclerView.Adapter<HomeListItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeListItemsAdapter.CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.homelist_item,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeListItemsAdapter.CustomViewHolder, position: Int) {
        holder.itemTitle.text = homeList.get(position).title
        holder.itemReward.text = homeList.get(position).reward.toString() + "원"




        holder.itemView.setOnClickListener{
            if(homeList.get(position).noticeToPanel?.length==0){
                val intent = Intent(holder.itemView.context, SurveyListDetailActivity::class.java)
                intent.putExtra("link", homeList.get(position).link)
                intent.putExtra("id", homeList.get(position).id)
                intent.putExtra("index",position)
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            }else{
                val intent = Intent(holder.itemView.context, NoticeToPanelDialogActivity::class.java)
                intent.putExtra("link", homeList.get(position).link)
                intent.putExtra("id", homeList.get(position).id)
                intent.putExtra("index",position)
                intent.putExtra("notice", homeList.get(position).noticeToPanel)
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            }


        }





    }

    override fun getItemCount(): Int {
        var cnt = 0
        if(homeList.size>3){
            cnt = 3
        }else{
            cnt = homeList.size
        }
        return cnt
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemBox : ConstraintLayout = itemView.findViewById(R.id.Homelist_listItemBox)
        val itemTitle : TextView = itemView.findViewById(R.id.HomeListItem_Title)
        val itemReward : TextView = itemView.findViewById(R.id.HomeListItem_Reward)
    }

}