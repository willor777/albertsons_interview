package com.willor.albertsons_interview.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willor.albertsons_interview.databinding.RvItemCardSearchresWithVariationsBinding
import com.willor.lib_data.domain.dataobjects.AcromineRespItem

class SearchResultsRvAdapter(
    val data: List<AcromineRespItem>
): RecyclerView.Adapter<SearchResultsRvAdapter.RvItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvItem {
        val binding = RvItemCardSearchresWithVariationsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RvItem(binding)
    }

    override fun onBindViewHolder(holder: RvItem, position: Int) {
        holder.initView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class RvItem(private val binding: RvItemCardSearchresWithVariationsBinding): RecyclerView.ViewHolder(binding.root){

        fun initView(acronym: AcromineRespItem){
            with(binding){
                tvShortform.text = acronym.shortForm
                tvLongFormMaster.text = acronym.longFormWithVariations[0].longForm
                rvLongFormVariations.adapter = LongFormVariationsAdapter(acronym.longFormWithVariations[0].variations)
            }
        }
    }


}