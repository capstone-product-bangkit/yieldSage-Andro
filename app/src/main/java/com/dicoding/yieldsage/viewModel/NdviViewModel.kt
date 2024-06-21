package com.dicoding.yieldsage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.yieldsage.data.response.ErrorResponse
import com.dicoding.yieldsage.data.response.GetNDVIResponse
import com.dicoding.yieldsage.data.response.UploadNDVIResponse
import com.dicoding.yieldsage.repository.Repository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class NdviViewModel(private val repository: Repository): ViewModel() {

    private val _ndviResponse = MutableLiveData<GetNDVIResponse>()
    val ndviResponse: LiveData<GetNDVIResponse> = _ndviResponse

    private val _upNdviResponse = MutableLiveData<UploadNDVIResponse>()
    val upNdviResponse: LiveData<UploadNDVIResponse> = _upNdviResponse


    fun getNdvi(){
        viewModelScope.launch {
            try {
                _ndviResponse.value = repository.getNdvi()
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
                _ndviResponse.value = GetNDVIResponse(errors = true, message = errorMessage)
            }
        }
    }

    fun uploadNdvi(file1: File, file2: File){
        viewModelScope.launch {
            try {
                _upNdviResponse.value = repository.uploadNdvi(file1, file2)
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
                _upNdviResponse.value = UploadNDVIResponse(errors = true, message = errorMessage)
            }
        }
    }
}