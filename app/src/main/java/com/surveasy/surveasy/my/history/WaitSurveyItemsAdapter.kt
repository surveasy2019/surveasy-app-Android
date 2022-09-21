package com.surveasy.surveasy.my.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surveasy.surveasy.R
import com.surveasy.surveasy.list.UserSurveyItem
import okhttp3.internal.wait

class WaitSurveyItemsAdapter(val waitList : ArrayList<UserSurveyItem>) : RecyclerView.Adapter<WaitSurveyItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)

        return CustomViewHolder(view)
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = waitList.get(position).title
        holder.itemDate.text = waitList.get(position).responseDate
        holder.itemReward.text = waitList.get(position).reward.toString() + "원"

        holder.itemReward.setOnClickListener {
            val intent_history : Intent = Intent(holder.itemView.context,MyViewHistoryDetailActivity::class.java)
            intent_history.putExtra("filePath", waitList.get(position).filePath)
            //storage 폴더 접근 위해
            intent_history.putExtra("id", waitList.get(position).id)
            intent_history.putExtra("idChecked", waitList.get(position).idChecked)
            intent_history.putExtra("title", waitList.get(position).title)
            intent_history.putExtra("date", waitList.get(position).responseDate)
            intent_history.putExtra("reward", waitList.get(position).reward)

            ContextCompat.startActivity(holder.itemView.context,intent_history,null)
        }
    }

    override fun getItemCount(): Int {
        return waitList.size
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle : TextView = itemView.findViewById(R.id.HistoryItem_Title)
        val itemDate : TextView = itemView.findViewById(R.id.HistoryItem_date)
        val itemReward : TextView = itemView.findViewById(R.id.HistoryItem_Reward)
        //val itemBtn : Button = itemView.findViewById(R.id.MyView_history_photo)
    }

}