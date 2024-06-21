package com.dicoding.yieldsage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.yieldsage.data.response.DataResult
import com.dicoding.yieldsage.data.response.DetailResult
import com.dicoding.yieldsage.data.response.ImageResultItem
import com.dicoding.yieldsage.repository.Repository
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: Repository): ViewModel() {

    private val _resultsResponse = MutableLiveData<List<DataResult>>()
    val resultsResponse: LiveData<List<DataResult>> = _resultsResponse

    private val _resultsIdResponse = MutableLiveData<DetailResult?>()
    val resultsIdResponse: LiveData<DetailResult?> = _resultsIdResponse

    fun getResults(){
        viewModelScope.launch {
            _resultsResponse.value = repository.getResults().data
        }
    }

    fun getResultId(id: String){
        viewModelScope.launch {
            _resultsIdResponse.value = repository.getResultId(id).data
        }
    }
}