package com.dicoding.yieldsage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.yieldsage.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}