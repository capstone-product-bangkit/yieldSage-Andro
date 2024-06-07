package com.dicoding.yieldsage.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMenu()
        setProfileMenu()
    }

    private fun setMenu(){
        binding.menuItem1.setOnClickListener {
            //TODO
        }
        binding.menuItem2.setOnClickListener {
            //TODO
        }
        binding.menuItem3.setOnClickListener {
            //TODO
        }
        binding.menuItem4.setOnClickListener {
            //TODO
        }
    }

    private fun setProfileMenu(){
        binding.profileLayout.setOnClickListener {
            //TODO
        }
    }
}