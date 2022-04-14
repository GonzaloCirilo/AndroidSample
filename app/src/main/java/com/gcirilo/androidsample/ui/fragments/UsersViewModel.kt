package com.gcirilo.androidsample.ui.fragments

import android.util.Log
import androidx.lifecycle.*
import com.gcirilo.androidsample.core.entities.User
import com.gcirilo.androidsample.core.networking.NetworkResponse
import com.gcirilo.androidsample.core.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UserRepositoryImpl
) : ViewModel() {

    var query: MutableLiveData<String> = MutableLiveData("")

    private var _errorMessage = MutableLiveData("")
    var errorMessage: LiveData<String> = _errorMessage.distinctUntilChanged()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading.distinctUntilChanged()

    var users: LiveData<List<User>> = Transformations.switchMap(query) {
        if(it.isNullOrEmpty())
            repository.getAll().asLiveData()
        else
            repository.filterByName(it).asLiveData()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().collect {
                if(it.isEmpty()) // If no data is in Room, load from service
                    refreshUsers()
            }
        }
    }

    private fun refreshUsers(){
        _isLoading.postValue(true)
        val response = repository.refreshUsers()
        viewModelScope.launch(Dispatchers.IO) {
            response.collect {
                when(it){
                    is NetworkResponse.Success<Array<User>> -> {
                        repository.insertUsers(it.data.orEmpty().toList())
                    }
                    is NetworkResponse.Failure<*> -> {
                        _errorMessage.postValue(it.error.message)
                    }
                    is NetworkResponse.Exception<*> -> {
                        _errorMessage.postValue(it.message)
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }

}