package com.dicoding.yieldsage.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.adapter.UploadAdapter
import com.dicoding.yieldsage.databinding.ActivityUploadNdviBinding
import com.dicoding.yieldsage.utils.tifUriToFile
import com.dicoding.yieldsage.viewModel.NdviViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory
import java.io.File

class UploadNDVIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadNdviBinding
    private lateinit var adapter: UploadAdapter
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NdviViewModel by viewModels{viewModelFactory}
    private val images = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadNdviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UploadAdapter(this, images)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter

        binding.OpenGallery.setOnClickListener {
            openGallery()
        }
        binding.UploadImage.setOnClickListener {
            uploadImages()
        }
    }

    private fun openGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun uploadImages(){
        showLoading(true)
        if (images.size < 2){
            showToast("Please select at least two images(RED and NIR)")
            return
        }
        val fileUris = images.take(2)
        val file1 = tifUriToFile(fileUris[0], this)
        val file2 = tifUriToFile(fileUris[1], this)

        viewModel.uploadNdvi(file1, file2)
        viewModel.upNdviResponse.observe(this){response->
            val intent = Intent(this, NDVIMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            showLoading(false)
            if (response.errors == true){
                showToast("${response.message}")
                startActivity(intent)
            } else {
                showToast("${response.message}")
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.OpenGallery.isEnabled = false
            binding.UploadImage.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.OpenGallery.isEnabled = true
            binding.UploadImage.isEnabled = true
        }
    }

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
        if (uris != null && uris.isNotEmpty()) {
            images.clear()
            val limitedUris = uris.take(2)
            images.addAll(limitedUris)
            adapter.notifyDataSetChanged()
        }
    }
}