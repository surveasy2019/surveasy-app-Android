package com.surveasy.surveasy.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amplitude.api.Amplitude
import com.surveasy.surveasy.R
import com.surveasy.surveasy.list.NoticeToPanelDialogActivity
import com.surveasy.surveasy.list.SurveyItems
import com.surveasy.surveasy.list.SurveyListDetailActivity


class HomeListItemsAdapter(val homeList: ArrayList<SurveyItems>) : RecyclerView.Adapter<HomeListItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeListItemsAdapter.CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_homelist,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeListItemsAdapter.CustomViewHolder, position: Int) {
        if(homeList.get(position).title.length > 18) holder.itemTitle.text = homeList.get(position).title.substring(0, 18) + "..."
        else holder.itemTitle.text = homeList.get(position).title
        holder.itemReward.text = homeList.get(position).reward.toString() + "원"




        holder.itemView.setOnClickListener{

            // [Amplitude] Home SurveyList Item Clicked
            val client = Amplitude.getInstance()
            client.logEvent("Home SurveyList Item Clicked")

            if(homeList.get(position).noticeToPanel?.length==0){
                val intent = Intent(holder.itemView.context, SurveyListDetailActivity::class.java)
                intent.putExtra("link", homeList.get(position).link)
                intent.putExtra("id", homeList.get(position).id)
                intent.putExtra("idChecked", homeList.get(position).idChecked)
                intent.putExtra("index",position)
                intent.putExtra("title",homeList.get(position).title)
                intent.putExtra("reward",homeList.get(position).reward)
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            }else{
                val intent = Intent(holder.itemView.context, NoticeToPanelDialogActivity::class.java)
                intent.putExtra("link", homeList.get(position).link)
                intent.putExtra("id", homeList.get(position).id)
                intent.putExtra("idChecked", homeList.get(position).idChecked)
                intent.putExtra("index",position)
                intent.putExtra("notice", homeList.get(position).noticeToPanel)
                intent.putExtra("title",homeList.get(position).title)
                intent.putExtra("reward",homeList.get(position).reward)
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