package com.gcirilo.androidsample.ui.fragments

import android.util.Log
import androidx.lifecycle.*
import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.core.networking.NetworkResponse
import com.gcirilo.androidsample.core.repository.PostRepository
import com.gcirilo.androidsample.core.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPostsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    private var postRepository: PostRepository
) : ViewModel() {
    // retrieve user id from nav args
    val id = savedStateHandle.get<Long>("id") ?: 0L

    val user = userRepository.getById(id).asLiveData().distinctUntilChanged()

    val posts = postRepository.getPostsByUserId(id).asLiveData().distinctUntilChanged()

    val postsCount = Transformations.map(posts){
        Log.d(UserPostsViewModel::class.java.simpleName, it.size.toString())
        it.size.toString()
    }

    private var _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage.distinctUntilChanged()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading.distinctUntilChanged()

    init {
        refreshPosts()
    }

    private fun refreshPosts(){
        _isLoading.postValue(true)
        val res = postRepository.refreshPostByUser(id)
        viewModelScope.launch(Dispatchers.IO) {
            res.collect {
                when(it) {
                    is NetworkResponse.Success<Array<Post>> -> {
                        postRepository.insertAll(it.data.orEmpty().toList())
                    }
                    is NetworkResponse.Exception<*> -> {
                        _errorMessage.postValue(it.message)
                    }
                    is NetworkResponse.Failure<*> -> {
                        _errorMessage.postValue(it.error.message)
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }

}