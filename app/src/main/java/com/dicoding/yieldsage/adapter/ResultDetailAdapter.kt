package com.dicoding.yieldsage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.yieldsage.data.response.ImageResultItem
import com.dicoding.yieldsage.databinding.ProjectImageItemBinding
import com.dicoding.yieldsage.databinding.ResultImageItemBinding

class ResultDetailAdapter (private val results: List<ImageResultItem>) : RecyclerView.Adapter<ResultDetailAdapter.MyViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultDetailAdapter.MyViewHolder {
        val binding = ResultImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)

    }

    override fun getItemCount(): Int = results.size

    inner class MyViewHolder(private val binding: ResultImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: ImageResultItem){
            Glide.with(itemView)
                .load(result.imageUrl)
                .into(binding.itemPhoto)
            binding.ageAverageDetail.text = result.ageAverage.toString()
            binding.cpaAverageDetail.text = result.cpaAverage.toString()
            binding.totalYieldDetail.text = result.totalYield.toString()
            binding.treeCountDetail.text = result.treeCount.toString()
        }
    }
}