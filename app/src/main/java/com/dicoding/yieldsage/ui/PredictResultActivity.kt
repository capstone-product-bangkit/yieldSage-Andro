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
import com.dicoding.yieldsage.adapter.ResultAdapter
import com.dicoding.yieldsage.databinding.ActivityPredictResultBinding
import com.dicoding.yieldsage.viewModel.ResultViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class PredictResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictResultBinding
    private lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: ResultViewModel by viewModels{viewModelFactory}

        val layoutManager = LinearLayoutManager(this)
        binding.resultRecyclerView.layoutManager = layoutManager

        viewModel.resultsResponse.observe(this){response->
            showLoading(false)
            adapter = ResultAdapter(response)
            binding.resultRecyclerView.adapter = adapter
        }


        viewModel.getResults()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}