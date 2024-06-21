package com.dicoding.yieldsage.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.adapter.ProjectAdapter
import com.dicoding.yieldsage.databinding.ActivityPredictMenuBinding
import com.dicoding.yieldsage.viewModel.PredictMenuViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class PredictMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictMenuBinding
    private lateinit var adapter: ProjectAdapter
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: PredictMenuViewModel by viewModels{viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        val layoutManager = LinearLayoutManager(this)
        binding.predictMenuRecyclerView.layoutManager = layoutManager

        viewModel.projectsResponse.observe(this){projectsResponse ->
            showLoading(false)
            if (projectsResponse.errors == false){
                adapter = ProjectAdapter(projectsResponse.data)
                binding.predictMenuRecyclerView.adapter = adapter
            }else{
                showToast("${projectsResponse.message}")
            }

        }

        viewModel.getProjects()
        setAddButton()
    }

    private fun setAddButton(){
        binding.floatingButton.setOnClickListener {
            val intent = Intent(this, CreateProjectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE

        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}