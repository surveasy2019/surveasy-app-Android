package com.surveasy.surveasy.presentation.introduce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surveasy.surveasy.databinding.ItemIntroducePageBinding
import com.surveasy.surveasy.presentation.introduce.model.UiFirstIntroduceData

class FirstIntroduceAdapter(
    private val data: UiFirstIntroduceData,
    private inline val clickStart: () -> Unit,
) : RecyclerView.Adapter<IntroduceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroduceViewHolder {
        return IntroduceViewHolder(
            ItemIntroducePageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.imgList.size
    }

    override fun onBindViewHolder(holder: IntroduceViewHolder, position: Int) {
        holder.bind(
            data.imgList[position],
            data.titleList[position],
            data.contentList[position],
            data.imgList.size == position + 1,
            clickStart
        )
    }
}

class IntroduceViewHolder(
    private val binding: ItemIntroducePageBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(img: Int, title: String, content: String, isLast: Boolean, listener: () -> Unit) =
        with(binding) {
            ivIntroduce.setImageResource(img)
            tvTitle.text = title
            tvContent.text = content
            btnStart.run {
                setOnClickListener { listener }
            }

            if (isLast) {
                btnStart.visibility = View.VISIBLE
                tvLastTitle.visibility = View.VISIBLE
            } else {
                btnStart.visibility = View.INVISIBLE
                tvLastTitle.visibility = View.GONE
            }
        }
}