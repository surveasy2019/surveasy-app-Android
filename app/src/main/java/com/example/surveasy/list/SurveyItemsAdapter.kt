package com.example.surveasy.list


import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import java.sql.RowId


class SurveyItemsAdapter(val surveyList: ArrayList<SurveyItems>, val boolList: ArrayList<Boolean>, val showCanParticipateList : ArrayList<Boolean>)
    : RecyclerView.Adapter<SurveyItemsAdapter.CustomViewHolder>() {


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



        if(!showCanParticipateList.contains(true)){
            if(boolList[position]){
                holder.itemTitle.setBackgroundColor(Color.RED)
            }
        }else{
            if(showCanParticipateList[position]){
                holder.itemBox.visibility = View.GONE
                holder.itemBox.layoutParams = ViewGroup.LayoutParams(0,0)
            }

        }

        val detailList : SurveyItems = SurveyItems(
            surveyList.get(position).id, surveyList.get(position).title,surveyList.get(position).target,
            surveyList.get(position).uploadDate,surveyList.get(position).link,surveyList.get(position).spendTime,
            surveyList.get(position).dueDate,surveyList.get(position).dueTimeTime,surveyList.get(position).reward,
            surveyList.get(position).noticeToPanel,surveyList.get(position).progress
        )

        holder.itemIcon.setOnClickListener{
            val intent = Intent(holder.itemView.context,SurveyListDetailDialogActivity::class.java)
            intent.putExtra("detailList", detailList)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }




        holder.itemView.setOnClickListener{
            if(surveyList.get(position).noticeToPanel?.length==0){
                val intent = Intent(holder.itemView.context,SurveyListDetailActivity::class.java)
                intent.putExtra("link", surveyList.get(position).link)
                intent.putExtra("id", surveyList.get(position).id)
                intent.putExtra("index",position)
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            }else{
                val intent = Intent(holder.itemView.context,NoticeToPanelDialogActivity::class.java)
                intent.putExtra("link", surveyList.get(position).link)
                intent.putExtra("id", surveyList.get(position).id)
                intent.putExtra("index",position)
                intent.putExtra("notice", surveyList.get(position).noticeToPanel)
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            }


        }

    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemBox : ConstraintLayout = itemView.findViewById(R.id.Surveylist_listItemBox)
        val itemTitle : TextView = itemView.findViewById(R.id.ListItem_Title)
        val itemDate : TextView = itemView.findViewById(R.id.ListItem_date)
        val itemIcon : ImageView = itemView.findViewById(R.id.ListItem_detailIcon)
    }
}