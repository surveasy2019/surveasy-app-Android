package com.surveasy.surveasy.presentation.main.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.surveasy.surveasy.databinding.ItemListHomeBinding
import com.surveasy.surveasy.presentation.main.home.model.UiHomeListData

class HomeListAdapter(
    private inline val clickItem: (Int) -> Unit
) : ListAdapter<UiHomeListData, HomeSurveyViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiHomeListData>() {
            override fun areItemsTheSame(
                oldItem: UiHomeListData,
                newItem: UiHomeListData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UiHomeListData,
                newItem: UiHomeListData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSurveyViewHolder {
        return HomeSurveyViewHolder(
            ItemListHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeSurveyViewHolder, position: Int) {
        holder.bind(getItem(position), clickItem)
    }

}

class HomeSurveyViewHolder(
    private val binding: ItemListHomeBinding
) : ViewHolder(binding.root) {
    fun bind(item: UiHomeListData, listener: (Int) -> Unit) = with(binding) {
        this.item = item
        root.run {
            setOnClickListener { listener(item.id) }
        }
        executePendingBindings()
    }
}