package com.dicoding.yieldsage.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.databinding.ActivityPredictMenuBinding

class PredictMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}