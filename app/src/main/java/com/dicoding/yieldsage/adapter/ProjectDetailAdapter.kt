package com.dicoding.yieldsage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.yieldsage.data.response.ImageContentItem
import com.dicoding.yieldsage.databinding.ProjectImageItemBinding

class ProjectDetailAdapter(private val projects: List<ImageContentItem>) : RecyclerView.Adapter<ProjectDetailAdapter.MyViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectDetailAdapter.MyViewHolder {
        val binding = ProjectImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)

    }

    override fun getItemCount(): Int = projects.size

    inner class MyViewHolder(private val binding: ProjectImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(project: ImageContentItem){
            Glide.with(itemView)
                .load(project.imageUrl)
                .into(binding.itemPhoto)

        }
    }
}