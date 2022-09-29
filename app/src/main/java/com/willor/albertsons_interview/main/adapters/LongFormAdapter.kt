package com.willor.albertsons_interview.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willor.albertsons_interview.databinding.RvItemCardLongFormBinding
import com.willor.lib_data.domain.dataobjects.LongFormWithVariations

class LongFormAdapter(
    val data: List<LongFormWithVariations>
): RecyclerView.Adapter<LongFormAdapter.LongFormCard>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LongFormCard {
        return LongFormCard(
            RvItemCardLongFormBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LongFormCard, position: Int) {
        holder.initView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class LongFormCard(val binding: RvItemCardLongFormBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun initView(longFormWithVariations: LongFormWithVariations){

                binding.longFormWithVariations = longFormWithVariations

                binding.rvLongFormVariations.adapter = LongFormVariationsAdapter(
                    longFormWithVariations.variations
                )

            }
        }


}