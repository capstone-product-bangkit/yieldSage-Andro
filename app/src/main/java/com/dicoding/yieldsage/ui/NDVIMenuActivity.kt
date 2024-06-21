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
import com.dicoding.yieldsage.adapter.NdviAdapter
import com.dicoding.yieldsage.databinding.ActivityNdvimenuBinding
import com.dicoding.yieldsage.viewModel.NdviViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class NDVIMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNdvimenuBinding
    private lateinit var adapter: NdviAdapter
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NdviViewModel by viewModels{viewModelFactory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNdvimenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        val layoutManager = LinearLayoutManager(this)
        binding.ndviMenuRecyclerView.layoutManager = layoutManager

        viewModel.ndviResponse.observe(this){response->
            showLoading(false)
            if (response.errors == false){
                adapter = NdviAdapter(response.data)
                binding.ndviMenuRecyclerView.adapter = adapter
            } else{
                showToast("${response.message}")
            }
        }

        binding.floatingButton.setOnClickListener {
            startActivity(Intent(this, UploadNDVIActivity::class.java))
        }

        viewModel.getNdvi()
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