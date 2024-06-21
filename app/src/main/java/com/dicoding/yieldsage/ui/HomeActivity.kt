package com.dicoding.yieldsage.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.yieldsage.R
import com.dicoding.yieldsage.data.local.preference.UserPreference
import com.dicoding.yieldsage.data.local.preference.dataStore
import com.dicoding.yieldsage.databinding.ActivityHomeBinding
import com.dicoding.yieldsage.viewModel.HomeViewModel
import com.dicoding.yieldsage.viewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: HomeViewModel by viewModels{viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener {menu->
            when(menu.itemId){
                R.id.logoutButton -> {
                    viewModel.logout()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    true
                }
                else-> false
            }
        }

//        binding.profileLayout.setOnClickListener {
//            Toast.makeText(this, "feature is under development", Toast.LENGTH_SHORT).show()
//        }

        setMenu()
        setProfileMenu()
    }

    private fun setMenu(){
        binding.cardItem1.setOnClickListener {
            val intent = Intent(this, PredictMenuActivity::class.java)
            startActivity(intent)
        }
        binding.cardItem2.setOnClickListener {
            val intent = Intent(this, PredictResultActivity::class.java)
            startActivity(intent)
        }
        binding.cardItem3.setOnClickListener {
            val intent = Intent(this, NDVIMenuActivity::class.java)
            startActivity(intent)
        }
//        binding.cardItem4.setOnClickListener {
//            Toast.makeText(this, "feature is under development", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun setProfileMenu(){
        val userPreference = UserPreference.getInstance(dataStore)
        CoroutineScope(Dispatchers.Main).launch {
            binding.profileName.text = userPreference.getUserName()
        }
    }
}