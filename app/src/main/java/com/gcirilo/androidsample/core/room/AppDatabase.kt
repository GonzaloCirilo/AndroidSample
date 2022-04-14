package com.gcirilo.androidsample.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.core.entities.User
import com.gcirilo.androidsample.core.room.dao.PostDao
import com.gcirilo.androidsample.core.room.dao.UserDao
import javax.inject.Singleton

@Singleton
@Database(entities = [
    Post::class,
    User::class,
],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
}