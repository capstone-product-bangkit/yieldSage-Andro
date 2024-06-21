package com.dicoding.yieldsage.repository

import android.util.Log
import com.dicoding.yieldsage.data.api.ApiService
import com.dicoding.yieldsage.data.local.model.UserModel
import com.dicoding.yieldsage.data.local.preference.UserPreference
import com.dicoding.yieldsage.data.response.CreateProjectResponse
import com.dicoding.yieldsage.data.response.GetNDVIResponse
import com.dicoding.yieldsage.data.response.GetProjectIdResponse
import com.dicoding.yieldsage.data.response.GetProjectResponse
import com.dicoding.yieldsage.data.response.GetResultIdResponse
import com.dicoding.yieldsage.data.response.GetResultResponse
import com.dicoding.yieldsage.data.response.LoginResponse
import com.dicoding.yieldsage.data.response.PredictResponse
import com.dicoding.yieldsage.data.response.RegisterResponse
import com.dicoding.yieldsage.data.response.UploadImageResponse
import com.dicoding.yieldsage.data.response.UploadNDVIResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class Repository (private val apiService: ApiService, private val userPreference: UserPreference){

    companion object {
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }

    suspend fun register(name: String, email: String, pNumber: String, password: String): RegisterResponse {
        val jsonBody = Gson().toJson(mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "phone_number" to pNumber
        ))
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())
        return apiService.register(requestBody)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val jsonBody = Gson().toJson(mapOf(
            "email" to email,
            "password" to password,
        ))
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())
        return apiService.login(requestBody)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun getProjects(): GetProjectResponse{
        return apiService.getProjects()
    }

    suspend fun createProject(title: String, description: String): CreateProjectResponse{
        val jsonBody = Gson().toJson(mapOf(
            "name" to title,
            "description" to description,
        ))
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())
        return apiService.createProject(requestBody)
    }

    suspend fun getProjectId(id: String): GetProjectIdResponse{
        return apiService.getProjectId(id)
    }

    suspend fun uploadImage(file: File, id: String): UploadImageResponse{
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestFile)

        return apiService.uploadImage(multipartBody, id)
    }

    suspend fun logout(){
        userPreference.logout()
    }

    suspend fun predict(id: String): PredictResponse{
        return apiService.predict(id)
    }

    suspend fun getResults(): GetResultResponse{
        return apiService.getResults()
    }

    suspend fun getResultId(id: String): GetResultIdResponse{
        return apiService.getResultId(id)
    }

    suspend fun getNdvi(): GetNDVIResponse{
        return apiService.getNdvi()
    }

    suspend fun uploadNdvi(file1: File, file2: File): UploadNDVIResponse{
        val requestFile1 = file1.asRequestBody("image/*".toMediaType())
        val requestFile2 = file2.asRequestBody("image/*".toMediaType())

        val multipartBody1 = MultipartBody.Part.createFormData("images", file1.name, requestFile1)
        val multipartBody2 = MultipartBody.Part.createFormData("images", file2.name, requestFile2)

        return apiService.uploadNdvi(multipartBody1, multipartBody2)
    }
}