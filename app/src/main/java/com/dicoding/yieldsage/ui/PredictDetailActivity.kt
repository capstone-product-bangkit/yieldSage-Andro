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
import com.dicoding.yieldsage.adapter.ProjectDetailAdapter
import com.dicoding.yieldsage.databinding.ActivityPredictDetailBinding
import com.dicoding.yieldsage.viewModel.PredictMenuViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class PredictDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictDetailBinding
    private lateinit var adapter: ProjectDetailAdapter
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: PredictMenuViewModel by viewModels{viewModelFactory}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        val layoutManager = LinearLayoutManager(this)
        binding.predictMenuRecyclerView.layoutManager = layoutManager
        val id = intent.getStringExtra("id").toString()

        binding.floatingButton.setOnClickListener{
            val intent = Intent(this, UploadImageActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        binding.predictButton.setOnClickListener {
            setPredict(id)
        }

        getDetail(id)
    }

    private fun getDetail(id: String){
        val title = intent.getStringExtra("title")
        binding.repoTitle.text = title
        viewModel.getProjectId(id)
        viewModel.projectIdResponse.observe(this){response->
            showLoading(false)
            if (response != null){
                binding.repoTitle.text = response.name
                adapter = ProjectDetailAdapter(response.imageContent)
                binding.predictMenuRecyclerView.adapter = adapter
            }
        }
    }

    private fun setPredict(id: String){
        showLoading(true)
        viewModel.startPredict(id)
        viewModel.predictResponse.observe(this){response->
            if (response.errors == false){
                showLoading(false)
                showToast("${response.message}")
            } else {
                showLoading(false)
                showToast("${response.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.predictButton.isEnabled = false

        } else {
            binding.progressBar.visibility = View.GONE
            binding.predictButton.isEnabled = true
        }
    }
}