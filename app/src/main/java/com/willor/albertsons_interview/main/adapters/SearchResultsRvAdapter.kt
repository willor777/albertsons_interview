package com.willor.albertsons_interview.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willor.albertsons_interview.databinding.RvItemAcromineRespItemBinding
import com.willor.lib_data.domain.dataobjects.AcromineRespItem

class SearchResultsRvAdapter(
    val data: List<AcromineRespItem>,
    val onItemClicked: (text: String) -> Unit
) : RecyclerView.Adapter<SearchResultsRvAdapter.RvItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvItem {
        val binding = RvItemAcromineRespItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RvItem(binding)
    }

    override fun onBindViewHolder(holder: RvItem, position: Int) {
        holder.initView(data[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class RvItem(private val binding: RvItemAcromineRespItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun initView(acronym: AcromineRespItem, onItemClicked: (String) -> Unit) {

            binding.acromineItem = acronym

            binding.tvSearchresulttitle.setOnClickListener {
                onItemClicked(acronym.shortForm)
            }
        }
    }


}