package com.dicoding.yieldsage.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.yieldsage.data.response.DataNdvi
import com.dicoding.yieldsage.databinding.NdviItemBinding
import com.dicoding.yieldsage.ui.PredictDetailActivity

class NdviAdapter(private val Ndvis: List<DataNdvi>) : RecyclerView.Adapter<NdviAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NdviItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Ndvi = Ndvis[position]
        holder.bind(Ndvi)

    }

    override fun getItemCount(): Int = Ndvis.size

    inner class MyViewHolder(private val binding: NdviItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ndvi: DataNdvi){
            binding.idText.text = "Id: ${ndvi.id}"
            binding.averageText.text = "Average Ndvi: ${ndvi.averageNdvi}"
            binding.healthText.text = "Healt Status: ${ndvi.healthStatus}"
        }
    }
}