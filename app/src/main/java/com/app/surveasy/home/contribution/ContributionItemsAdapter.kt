package com.app.surveasy.home.contribution

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.surveasy.R

class ContributionItemsAdapter(val contributionList : ArrayList<ContributionItems>)
    : RecyclerView.Adapter<ContributionItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contribution,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val contribution : ContributionItems = contributionList[position]

        if(contribution.title.length > 18) holder.contributionItemTitle.text = contribution.title.substring(0, 18) + "..."
        else { holder.contributionItemTitle.text = contribution.title }

        if(contribution.institute.length > 25) holder.contributionItemInstitute.text = contribution.institute.substring(0, 25) + "..."
        else { holder.contributionItemInstitute.text = contribution.institute }

        holder.contributionItemDate.text = contribution.date + " 설문 진행"

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView!!.context, HomeContributionListDetailActivity::class.java)
            intent.putExtra("date", contribution.date)
            intent.putExtra("title", contribution.title)
            intent.putExtra("journal", contribution.journal)
            intent.putExtra("source", contribution.source)
            intent.putExtra("institute", contribution.institute)
            intent.putExtra("img", contribution.img)
            intent.putExtra("content", contribution.content)

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