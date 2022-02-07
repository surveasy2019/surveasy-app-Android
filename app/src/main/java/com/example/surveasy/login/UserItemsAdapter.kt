package com.example.surveasy.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R

class UserItemsAdapter(val userList : ArrayList<UserItems>)
    : RecyclerView.Adapter<UserItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val user : UserItems = userList[position]
        holder.userItemUID.text = user.uid
        holder.userItemEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userItemUID : TextView = itemView.findViewById(R.id.UserItem_UID)
        val userItemEmail : TextView = itemView.findViewById(R.id.UserItem_Email)
    }


}