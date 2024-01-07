package com.surveasy.surveasy.presentation.main.my.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.surveasy.surveasy.databinding.ItemHistoryBinding
import com.surveasy.surveasy.presentation.main.my.history.model.UiHistorySurveyData

class HistoryAdapter(
    private inline val clickItem: (Int) -> Unit
) : ListAdapter<UiHistorySurveyData, HistoryViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiHistorySurveyData>() {
            override fun areItemsTheSame(
                oldItem: UiHistorySurveyData,
                newItem: UiHistorySurveyData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UiHistorySurveyData,
                newItem: UiHistorySurveyData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position), clickItem)
    }
}

class HistoryViewHolder(
    private val binding: ItemHistoryBinding
) : ViewHolder(binding.root) {
    fun bind(item: UiHistorySurveyData, listener: (Int) -> Unit) = with(binding) {
        this.item = item
        ivDetail.setOnClickListener { listener(item.id) }
        executePendingBindings()
    }
}
