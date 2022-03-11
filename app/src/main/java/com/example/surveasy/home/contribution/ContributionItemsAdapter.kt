package com.example.surveasy.home.contribution

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R

class ContributionItemsAdapter(val contributionList : ArrayList<ContributionItems>)
    : RecyclerView.Adapter<ContributionItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contribution,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val contribution : ContributionItems = contributionList[position]
        holder.contributionItemTitle.text = contribution.title.substring(0, 17) + "..."
        holder.contributionItemDate.text = contribution.date + " 설문 진행"
        holder.contributionItemInstitute.text = contribution.institute


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView!!.context, HomeContributionListDetailActivity::class.java)
            intent.putExtra("title", contribution.title)
            intent.putExtra("date", contribution.date)
            intent.putExtra("institute", contribution.institute)
            intent.putExtra("img", contribution.img)
            intent.putStringArrayListExtra("content", contribution.content)

            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return contributionList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contributionItemTitle : TextView = itemView.findViewById(R.id.ContributionItem_Title)
        val contributionItemInstitute : TextView = itemView.findViewById(R.id.ContributionItem_Institute)
        val contributionItemDate: TextView = itemView.findViewById(R.id.ContributionItem_Date)

    }


}