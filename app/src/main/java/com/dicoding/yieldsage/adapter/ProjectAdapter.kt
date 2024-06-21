package com.dicoding.yieldsage.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.yieldsage.data.response.DataItem
import com.dicoding.yieldsage.databinding.ProjectItemBinding
import com.dicoding.yieldsage.ui.PredictDetailActivity

class ProjectAdapter(private val projects: List<DataItem>) :RecyclerView.Adapter<ProjectAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)

    }

    override fun getItemCount(): Int = projects.size

    inner class MyViewHolder(private val binding: ProjectItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(project: DataItem){
            binding.itemTitle.text = project.name
            binding.itemDescription.text = project.description

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, PredictDetailActivity::class.java)
                intentDetail.putExtra("title", project.name)
                intentDetail.putExtra("id", project.id)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.itemTitle, "title"),
                    )
                itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
            }
        }
    }
}