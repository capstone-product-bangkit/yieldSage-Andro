package com.dicoding.yieldsage.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.yieldsage.databinding.ActivityCreateProjectBinding
import com.dicoding.yieldsage.viewModel.PredictMenuViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory

class CreateProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateProjectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setCreateButton()
    }

    private fun setCreateButton(){
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: PredictMenuViewModel by viewModels{viewModelFactory}
        binding.createButton.setOnClickListener {
            val projectName = binding.titleEditText.text.toString().trim()
            val projectDesc = binding.descriptionEditText.text.toString().trim()
            if (projectName.isNotEmpty() && projectDesc.isNotEmpty()){
                showLoading(true)
                viewModel.createProjects(projectName, projectDesc)
                viewModel.createResponse.observe(this){response->
                    if (response.errors == false){
                        showLoading(false)
                        showToast("${response.message}")
                        val intent = Intent(this, PredictMenuActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        showLoading(false)
                        showToast("${response.message}")
                    }
                }
            } else{
                showToast("TextField is Empty")
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.createButton.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.createButton.isEnabled = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}