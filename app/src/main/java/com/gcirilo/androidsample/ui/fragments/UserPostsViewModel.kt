package com.gcirilo.androidsample.ui.fragments

import androidx.lifecycle.*
import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.core.networking.NetworkResponse
import com.gcirilo.androidsample.core.repository.PostRepository
import com.gcirilo.androidsample.core.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    // Flows from repo
    private val userFlow = userRepository.getById(id)
    private val postsFlow = postRepository.getPostsByUserId(id)

    // Presenting values
    val username = userFlow.map { it.name }.asStateFlow("Name")
    val phone = userFlow.map { it.phone }.asStateFlow("")
    val email = userFlow.map { it.email }.asStateFlow("")

    val posts = postsFlow.asStateFlow(emptyList())
    val postsCount = postsFlow.map { it.size.toString() }.asStateFlow("0")

    private var _errorMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        refreshPosts()
    }

    private fun <T> Flow<T>.asStateFlow(initialValue: T): StateFlow<T> {
        val mStateFlow = MutableStateFlow(initialValue)
        val stateFlow = mStateFlow.asStateFlow()
        viewModelScope.launch {
            this@asStateFlow.collect {
                mStateFlow.emit(it)
            }
        }
        return stateFlow
    }

    private fun refreshPosts(){
        val res = postRepository.refreshPostByUser(id)
        viewModelScope.launch {
            _isLoading.emit(true)
            res.collect {
                when(it) {
                    is NetworkResponse.Success<Array<Post>> -> {
                        postRepository.insertAll(it.data.orEmpty().toList())
                    }
                    is NetworkResponse.Exception<*> -> {
                        _errorMessage.emit(it.message)
                    }
                    is NetworkResponse.Failure<*> -> {
                        _errorMessage.emit(it.error.message.orEmpty())
                    }
                }
                _isLoading.value = (false)
            }
        }
    }

}