package com.gcirilo.androidsample.core

import com.gcirilo.androidsample.core.networking.NetworkResponse
import com.gcirilo.androidsample.core.repository.PostRepository
import com.gcirilo.androidsample.core.repository.PostRepositoryImpl
import com.gcirilo.androidsample.core.room.dao.PostDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class PostRepositoryUnitTest {

    @Test
    fun refreshPostsIsCorrect() {
        // Given
        val mockPostDao = mock<PostDao> {}
        val repository: PostRepository = PostRepositoryImpl(fakeNetworkManager(), mockPostDao)
        // When
        val response = repository.refreshPostByUser(1)
        // Then
        runBlocking {
            assert(response.first() is NetworkResponse.Success<*>)
        }
    }

    @Test
    fun getPostsByUserIdIsCorrect(){
        // Given
        val mockPostDao = mock<PostDao> {
            on { getPostByUserId(1) } doReturn flow { emit(listOf(fakePost())) }
        }
        val repository: PostRepository = PostRepositoryImpl(fakeNetworkManager(), mockPostDao)
        // When
        val response = repository.getPostsByUserId(1)
        // Then
        runBlocking {
            assertEquals(response.first().size, 1)
        }
    }

    @Test
    fun insertAllPostsIsCorrect() {
        // Given
        val posts = listOf(fakePost())
        val mockPostDao = mock<PostDao> {
            onBlocking { insertAll(posts) } doReturn Unit
        }
        val repository: PostRepository = PostRepositoryImpl(fakeNetworkManager(), mockPostDao)
        // When
        repository.insertAll(posts)
        // Then exception must not occur

    }
}