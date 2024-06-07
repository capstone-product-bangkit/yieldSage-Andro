package com.dicoding.yieldsage.repository

import com.dicoding.yieldsage.data.api.ApiService
import com.dicoding.yieldsage.data.local.preference.UserPreference

class Repository (private val apiService: ApiService, private val userPreference: UserPreference){

    companion object {
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}