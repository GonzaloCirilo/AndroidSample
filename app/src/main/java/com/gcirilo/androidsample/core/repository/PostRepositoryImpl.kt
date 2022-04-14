package com.gcirilo.androidsample.core.repository

import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.core.networking.NetworkManager
import com.gcirilo.androidsample.core.room.dao.PostDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private var networkManager: NetworkManager,
    private var postDao: PostDao,
): PostRepository {
    // Room methods
    override fun insertAll(posts: List<Post>) {
        runBlocking(Dispatchers.IO) {
            postDao.insertAll(posts)
        }
    }

    override fun getPostsByUserId(userId: Long) = postDao.getPostByUserId(userId)

    // Remote methods

    override fun refreshPostByUser(userId: Long) = networkManager.get("/posts?userId=$userId", Array<Post>::class.java)


}