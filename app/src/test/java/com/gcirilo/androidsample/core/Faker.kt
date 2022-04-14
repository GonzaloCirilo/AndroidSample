package com.gcirilo.androidsample.core

import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.core.entities.User
import com.gcirilo.androidsample.core.networking.NetworkManager
import com.gcirilo.androidsample.core.networking.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun fakeUser(): User = User(1, "Test User", "888888-2", "test@tes123.asd")

fun fakeNetworkManager(): NetworkManager = object : NetworkManager {
    override fun <T> get(relURL: String, clazz: Class<T>): Flow<NetworkResponse<T>> {
        return flow { emit(NetworkResponse.Success()) }
    }
}

fun fakePost() = Post(1, 1, "Title Test", "Sentence goes here.")
