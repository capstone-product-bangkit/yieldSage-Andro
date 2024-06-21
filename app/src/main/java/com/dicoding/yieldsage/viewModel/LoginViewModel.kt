package com.dicoding.yieldsage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.yieldsage.data.local.model.UserModel
import com.dicoding.yieldsage.data.response.ErrorResponse
import com.dicoding.yieldsage.data.response.LoginResponse
import com.dicoding.yieldsage.repository.Repository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val repository: Repository): ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginResponse.value = response
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
                _loginResponse.value = LoginResponse(errors = true, message = errorMessage)
            }

        }
    }
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}