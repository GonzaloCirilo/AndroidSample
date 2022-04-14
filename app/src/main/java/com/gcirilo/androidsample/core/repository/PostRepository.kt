package com.gcirilo.androidsample.core.repository

import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.core.networking.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun insertAll(posts: List<Post>)

    fun getPostsByUserId(userId: Long): Flow<List<Post>>

    fun refreshPostByUser(userId: Long): Flow<NetworkResponse<Array<Post>>>
}