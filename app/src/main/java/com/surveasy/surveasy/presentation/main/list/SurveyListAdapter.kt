package com.surveasy.surveasy.presentation.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.surveasy.surveasy.databinding.ItemListDoneBinding
import com.surveasy.surveasy.databinding.ItemListOngoingBinding
import com.surveasy.surveasy.presentation.main.list.model.UiSurveyListData

class SurveyListAdapter(
    private inline val clickItem: (Int) -> Unit
) : ListAdapter<UiSurveyListData, ViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiSurveyListData>() {
            override fun areItemsTheSame(
                oldItem: UiSurveyListData,
                newItem: UiSurveyListData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UiSurveyListData,
                newItem: UiSurveyListData
            ): Boolean {
                return oldItem == newItem
            }
        }

        const val ONGOING = 0
        const val DONE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ONGOING -> OngoingSurveyViewHolder(
                ItemListOngoingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> DoneSurveyViewHolder(
                ItemListDoneBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ONGOING -> (holder as OngoingSurveyViewHolder).bind(getItem(position), clickItem)
            else -> (holder as DoneSurveyViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).participated) {
            DONE
        } else {
            ONGOING
        }
    }

}

class OngoingSurveyViewHolder(
    private val binding: ItemListOngoingBinding
) : ViewHolder(binding.root) {
    fun bind(item: UiSurveyListData, listener: (Int) -> Unit) = with(binding) {
        root.run {
            setOnClickListener { listener(item.id) }
        }
        this.item = item
        executePendingBindings()
    }
}

class DoneSurveyViewHolder(
    private val binding: ItemListDoneBinding
) : ViewHolder(binding.root) {
    fun bind(item: UiSurveyListData) = with(binding) {
        this.item = item
        executePendingBindings()
    }
}