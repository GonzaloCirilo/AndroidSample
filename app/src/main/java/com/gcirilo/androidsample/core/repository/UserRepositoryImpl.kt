package com.gcirilo.androidsample.core.repository

import com.gcirilo.androidsample.core.entities.User
import com.gcirilo.androidsample.core.networking.NetworkManager
import com.gcirilo.androidsample.core.room.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private var networkManager: NetworkManager,
    private var userDao: UserDao
): UserRepository {
    // Room methods
    override fun insertUsers(users: List<User>) {
        runBlocking(Dispatchers.IO) { // use I/O thread, avoiding working in Main thread
            userDao.insertAll(users)
        }
    }

    override fun getAll() = userDao.getAll()

    override fun getById(id: Long) = userDao.getById(id)

    override fun filterByName(query: String): Flow<List<User>> = userDao.getAllByNameContaining(query)

    // Remote methods
    override fun refreshUsers() = networkManager.get("/users", Array<User>::class.java)

}