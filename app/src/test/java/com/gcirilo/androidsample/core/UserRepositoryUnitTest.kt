package com.gcirilo.androidsample.core

import com.gcirilo.androidsample.core.networking.NetworkResponse
import com.gcirilo.androidsample.core.repository.UserRepository
import com.gcirilo.androidsample.core.repository.UserRepositoryImpl
import com.gcirilo.androidsample.core.room.dao.UserDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryUnitTest {

    @Test
    fun refreshUsersIsCorrect() {
        // Given
        val mockUserDao = mock<UserDao> {  }
        val repository: UserRepository = UserRepositoryImpl(fakeNetworkManager(), mockUserDao)
        // When
        val response = repository.refreshUsers()
        // Then
        runBlocking {
            assert(response.first() is NetworkResponse.Success<*>)
        }
    }

    @Test
    fun getAllIsCorrect() {
        // Given
        val mockUserDao = mock<UserDao> {
            on { getAll() } doReturn flow { emit(listOf(fakeUser())) }
        }
        val repository: UserRepository = UserRepositoryImpl(fakeNetworkManager(), mockUserDao)
        // When
        val response = repository.getAll()
        // Then
        runBlocking {
            assertEquals(response.first().size, 1)
        }
    }

    @Test
    fun getByIdIsCorrect() {
        // Given
        val mockUserDao = mock<UserDao> {
            on { getById(1) } doReturn flow { emit(fakeUser()) }
        }
        val repository: UserRepository = UserRepositoryImpl(fakeNetworkManager(), mockUserDao)
        // When
        val response = repository.getById(1)
        // Then
        runBlocking {
            assertEquals(response.first().name,"Test User")
        }
    }


    @Test
    fun insertAllIsCorrect() {
        // Given
        val users = listOf(fakeUser())
        val mockUserDao = mock<UserDao> {
            onBlocking { insertAll(users) } doReturn Unit
        }
        val repository: UserRepository = UserRepositoryImpl(fakeNetworkManager(), mockUserDao)
        // When
        repository.insertUsers(users)
        // Then exception must not occur
    }

    @Test
    fun filterByNameIsCorrect() {
        // Given
        val query = "Test"
        val mockUserDao = mock<UserDao> {
            on { getAllByNameContaining(query) } doReturn flow { emit(listOf(fakeUser())) }
        }
        val repository: UserRepository = UserRepositoryImpl(fakeNetworkManager(), mockUserDao)
        // When
        val response = repository.filterByName(query)
        // Then
        runBlocking {
            assertEquals(response.first().size, 1)
        }
    }
}

