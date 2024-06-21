package com.dicoding.yieldsage.di

import android.content.Context
import com.dicoding.yieldsage.data.api.ApiConfig
import com.dicoding.yieldsage.data.local.preference.UserPreference
import com.dicoding.yieldsage.data.local.preference.dataStore
import com.dicoding.yieldsage.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(pref)
        return Repository.getInstance(apiService, pref)
    }
}