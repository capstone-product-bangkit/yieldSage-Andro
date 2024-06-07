package com.dicoding.yieldsage.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.databinding.ActivityPredictResultBinding

class PredictResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}