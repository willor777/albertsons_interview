package com.willor.albertsons_interview.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willor.albertsons_interview.databinding.RvItemInnerVariationCardBinding
import com.willor.lib_data.domain.dataobjects.Variation


/**
 * Recycler view to display Long Form Variations of Acronyms. Will be used inside the Cardview,
 * which is the MainActivity Recycler View's Item
 */
class LongFormVariationsAdapter(
    val data: List<Variation>
) : RecyclerView.Adapter<LongFormVariationsAdapter.LfVariationItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LfVariationItem {
        val binding = RvItemInnerVariationCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LfVariationItem(binding)
    }

    override fun onBindViewHolder(holder: LfVariationItem, position: Int) {
        holder.initView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class LfVariationItem(
        private val binding: RvItemInnerVariationCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun initView(variation: Variation) {
            binding.longFormVariation = variation
            binding.longFormFreq = variation.freq.toString()
            binding.longFormUsedSince = variation.freq.toString()
        }
    }
}