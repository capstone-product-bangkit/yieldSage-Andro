package com.dicoding.yieldsage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.yieldsage.data.response.CreateProjectResponse
import com.dicoding.yieldsage.data.response.DataItem
import com.dicoding.yieldsage.data.response.DataRepo
import com.dicoding.yieldsage.data.response.ErrorResponse
import com.dicoding.yieldsage.data.response.GetProjectResponse
import com.dicoding.yieldsage.data.response.PredictResponse
import com.dicoding.yieldsage.data.response.UploadImageResponse
import com.dicoding.yieldsage.repository.Repository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class PredictMenuViewModel(private val repository: Repository): ViewModel() {

    private val _projectsResponse = MutableLiveData<GetProjectResponse>()
    val projectsResponse: LiveData<GetProjectResponse> = _projectsResponse

    private val _createResponse = MutableLiveData<CreateProjectResponse>()
    val createResponse: LiveData<CreateProjectResponse> = _createResponse

    private val _projectIdResponse = MutableLiveData<DataRepo?>()
    val projectIdResponse: LiveData<DataRepo?> = _projectIdResponse

    private val _uploadResponse = MutableLiveData<UploadImageResponse>()
    val uploadResponse: LiveData<UploadImageResponse> = _uploadResponse

    private val _uploadProgress = MutableLiveData<Int>()
    val uploadProgress: LiveData<Int> = _uploadProgress

    private val _predictResponse = MutableLiveData<PredictResponse>()
    val predictResponse: LiveData<PredictResponse> = _predictResponse

    private var progress = 0
    fun getProjects(){
        viewModelScope.launch {
            try {
                _projectsResponse.value = repository.getProjects()
            } catch (e: Exception){
                val errorMessage = when (e) {
                    is HttpException -> {
                        try {
                            val errorBody = e.response()?.errorBody()?.string()
                            if (errorBody != null) {
                                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                                errorResponse.message
                            } else {
                                "Unknown error occurred"
                            }
                        } catch (jsonException: JsonSyntaxException) {
                            "Error parsing error message"
                        } catch (exception: Exception) {
                            "Unknown error occurred"
                        }
                    }
                    is IOException -> {
                        "Network error occurred"
                    }
                    else -> {
                        "Unknown error occurred"
                    }
                }
                _projectsResponse.value = GetProjectResponse(errors = true, message = errorMessage)
            }
        }
    }

    fun createProjects(title: String, description: String){
        viewModelScope.launch {
            try {
                val response = repository.createProject(title, description)
                _createResponse.value  = response
            }catch (e: Exception){
                val errorMessage = when (e) {
                    is HttpException -> {
                        try {
                            val errorBody = e.response()?.errorBody()?.string()
                            if (errorBody != null) {
                                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                                errorResponse.message
                            } else {
                                "Unknown error occurred"
                            }
                        } catch (jsonException: JsonSyntaxException) {
                            "Error parsing error message"
                        } catch (exception: Exception) {
                            "Unknown error occurred"
                        }
                    }
                    is IOException -> {
                        "Network error occurred"
                    }
                    else -> {
                        "Unknown error occurred"
                    }
                }
                _createResponse.value = CreateProjectResponse(errors = true, message = errorMessage)
            }
        }
    }

    fun getProjectId(id: String){
        viewModelScope.launch {
            _projectIdResponse.value = repository.getProjectId(id).data
        }
    }

    fun uploadImage(file: File, id: String){
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(file, id)
                _uploadResponse.value = response
                if (response.errors == false){
                    progress++
                    _uploadProgress.value = progress
                } else {
                    progress++
                    _uploadProgress.value = progress
                }
            } catch (e: Exception){
                _uploadResponse.value = UploadImageResponse(errors = true, message = e.message ?: "Unknown error occurred")
            }
        }
    }

    fun startPredict(id: String){
        viewModelScope.launch {
            try {
                val response = repository.predict(id)
                _predictResponse.value = response
            } catch (e: Exception){
                val errorMessage = when (e) {
                    is HttpException -> {
                        try {
                            val errorBody = e.response()?.errorBody()?.string()
                            if (errorBody != null) {
                                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                                errorResponse.message
                            } else {
                                "Unknown error occurred"
                            }
                        } catch (jsonException: JsonSyntaxException) {
                            "Error parsing error message"
                        } catch (exception: Exception) {
                            "Unknown error occurred"
                        }
                    }
                    is IOException -> {
                        "Network error occurred"
                    }
                    else -> {
                        "Unknown error occurred"
                    }
                }
                _predictResponse.value = PredictResponse(errors = true, message = errorMessage)
            }
        }
    }
}