package com.dicoding.yieldsage.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.yieldsage.adapter.UploadAdapter
import com.dicoding.yieldsage.databinding.ActivityUploadImageBinding
import com.dicoding.yieldsage.utils.uriToFile
import com.dicoding.yieldsage.viewModel.PredictMenuViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class UploadImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadImageBinding
    private lateinit var adapter: UploadAdapter
    private val images = mutableListOf<Uri>()
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: PredictMenuViewModel by viewModels{viewModelFactory}


    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
        if (uris != null && uris.isNotEmpty()) {
            images.clear()
            images.addAll(uris)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
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
        val id = intent.getStringExtra("id").toString()
        val imagesSize = images.size
//        Log.e("images", imagesSize.toString())
        showLoading(true)
        images.let { images->
            showLoading(true)
            for (image in images){
                val imageFile = uriToFile(image, this)
                viewModel.uploadImage(imageFile, id)
                viewModel.uploadResponse.observe(this){ response->
                    if (response.errors == true){
                        showToast("Failed to upload Image: $image")
                    }
                }
            }
            viewModel.uploadProgress.observe(this){progress ->
                Log.e("progress", progress.toString())
                if (progress == imagesSize){
                    showLoading(false)
                    val intent = Intent(this, PredictDetailActivity::class.java)
                    intent.putExtra("id", id)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
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
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}