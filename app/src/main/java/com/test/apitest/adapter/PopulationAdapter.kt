package com.test.apitest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.apitest.databinding.PopulationDataLayoutBinding
import com.test.apitest.models.Data

class PopulationAdapter : RecyclerView.Adapter<PopulationAdapter.ViewHolder>() {

    var list = ArrayList<Data>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: PopulationDataLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.population.text = data.population.toString()
            binding.idNation.text = data.idNation
            binding.idYear.text = data.idYear.toString()
            binding.nation.text = data.nation
            binding.slugNation.text = data.slugNation
            binding.year.text = data.year
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PopulationDataLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}