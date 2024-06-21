package com.dicoding.yieldsage.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.adapter.ResultDetailAdapter
import com.dicoding.yieldsage.databinding.ActivityResultDetailBinding
import com.dicoding.yieldsage.viewModel.ResultViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class ResultDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetailBinding
    private lateinit var adapter: ResultDetailAdapter
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: ResultViewModel by viewModels{viewModelFactory}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        val layoutManager = LinearLayoutManager(this)
        binding.resultImageRecyclerView.layoutManager = layoutManager

        val id = intent.getStringExtra("id").toString()
        val title = intent.getStringExtra("title")
        binding.repoTitle.text = title

        viewModel.resultsIdResponse.observe(this){response->
            showLoading(false)
            if (response != null){
                adapter = ResultDetailAdapter(response.imageContent)
                binding.resultImageRecyclerView.adapter = adapter
                binding.ageAverageDetail.text = response.ageAverage.toString()
                binding.cpaAverageDetail.text = response.cpaAverage.toString()
                binding.totalYieldDetail.text = response.totalYield.toString()
                binding.treeCountDetail.text = response.treeCount.toString()
            }
        }

        viewModel.getResultId(id)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}