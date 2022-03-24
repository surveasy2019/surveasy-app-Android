package com.app.surveasy.list


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.surveasy.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SurveyItemsAdapter(val surveyList: ArrayList<SurveyItems>, val boolList: ArrayList<Boolean>, val showCanParticipateList : ArrayList<Boolean>)
    : RecyclerView.Adapter<SurveyItemsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{

            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        //title 길이
        val fullTitle = surveyList.get(position).title
        var shortTitle = ""
        if(fullTitle.length>20){
            shortTitle = fullTitle.substring(0,20)+".."
        }else{
            shortTitle = fullTitle
        }

        //dDay 계산
        val dueDate = surveyList.get(position).dueDate+" "+surveyList.get(position).dueTimeTime+":00"
        var sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = sf.parse(dueDate)
        var now = Calendar.getInstance()
        var calDate = (date.time - now.time.time) / (60*60*1000)
        var dDay = ""
        if(calDate<0){
            dDay = "마감"
        }else{
            if(calDate<24){
                dDay = calDate.toString()+"시간 후 마감"
            }else{
                dDay = "D - "+(calDate/24).toString()
            }
        }

        holder.itemTitle.text = shortTitle
        holder.itemDate.text = dDay
        holder.itemSpendTime.text = surveyList.get(position).spendTime
        holder.itemTarget.text = "대상 : "+surveyList.get(position).target
        holder.itemReward.text = surveyList.get(position).reward.toString() + "원"

        //참여한 설문 박스 색 변경



        if(!showCanParticipateList.contains(true)){
            if(boolList[position]){
                holder.itemContainer.setBackgroundResource(R.drawable.custom_donbox)

            }
        }else{
            if(showCanParticipateList[position]){
                holder.itemBox.visibility = View.GONE
                holder.itemBox.layoutParams = ViewGroup.LayoutParams(0,0)
            }

        }

//        val detailList : SurveyItems = SurveyItems(
//            surveyList.get(position).id, surveyList.get(position).title,surveyList.get(position).target,
//            surveyList.get(position).uploadDate,surveyList.get(position).link,surveyList.get(position).spendTime,
//            surveyList.get(position).dueDate,surveyList.get(position).dueTimeTime,surveyList.get(position).reward,
//            surveyList.get(position).noticeToPanel,surveyList.get(position).progress
//        )

//        holder.itemIcon.setOnClickListener{
//            val intent = Intent(holder.itemView.context,SurveyListDetailDialogActivity::class.java)
//            intent.putExtra("detailList", detailList)
//            ContextCompat.startActivity(holder.itemView.context,intent,null)
//        }




        holder.itemView.setOnClickListener{
            if(!showCanParticipateList.contains(true) && boolList[position]){

            }else{
                if(surveyList.get(position).noticeToPanel?.length==0){
                    val intent = Intent(holder.itemView.context,SurveyListDetailActivity::class.java)
                    intent.putExtra("link", surveyList.get(position).link)
                    intent.putExtra("title", surveyList.get(position).title)
                    intent.putExtra("id", surveyList.get(position).id)
                    intent.putExtra("index",position)
                    intent.putExtra("reward",surveyList.get(position).reward)
                    ContextCompat.startActivity(holder.itemView.context,intent,null)
                }else{
                    val intent = Intent(holder.itemView.context,NoticeToPanelDialogActivity::class.java)
                    intent.putExtra("link", surveyList.get(position).link)
                    intent.putExtra("title", surveyList.get(position).title)
                    intent.putExtra("id", surveyList.get(position).id)
                    intent.putExtra("index",position)
                    intent.putExtra("notice", surveyList.get(position).noticeToPanel)
                    intent.putExtra("reward",surveyList.get(position).reward)
                    ContextCompat.startActivity(holder.itemView.context,intent,null)
                }



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
        val itemTarget : TextView = itemView.findViewById(R.id.ListItem_target)
        val itemSpendTime : TextView = itemView.findViewById(R.id.ListItem_spendTime)
        val itemReward : TextView = itemView.findViewById(R.id.ListItem_reward)
        val itemContainer : LinearLayout = itemView.findViewById(R.id.SurveyList_ItemContainer)



//        val itemIcon : ImageView = itemView.findViewById(R.id.ListItem_detailIcon)

    }
}