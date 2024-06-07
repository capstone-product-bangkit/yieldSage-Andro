package com.dicoding.yieldsage.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.databinding.ActivityResultDetailBinding

class ResultDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}