package com.gcirilo.androidsample.core.repository

import com.gcirilo.androidsample.core.entities.User
import com.gcirilo.androidsample.core.networking.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun insertUsers(users: List<User>)

    fun getAll(): Flow<List<User>>

    fun getById(id: Long): Flow<User>

    fun refreshUsers(): Flow<NetworkResponse<Array<User>>>

    fun filterByName(query: String): Flow<List<User>>
}