package com.dicoding.yieldsage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.yieldsage.data.response.ErrorResponse
import com.dicoding.yieldsage.data.response.RegisterResponse
import com.dicoding.yieldsage.repository.Repository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel(private val repository: Repository): ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun register(name: String, email: String, pNumber: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, pNumber, password)
                _registerResponse.value = response
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
                _registerResponse.value = RegisterResponse(errors = true, message = errorMessage)
            }
        }
    }
}