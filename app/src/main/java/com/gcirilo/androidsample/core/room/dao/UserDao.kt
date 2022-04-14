package com.gcirilo.androidsample.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gcirilo.androidsample.core.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM user")
    abstract fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE name LIKE '%' || :query || '%' ")
    abstract fun getAllByNameContaining(query: String): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    abstract fun getById(id: Long): Flow<User>
}