package com.gcirilo.androidsample.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gcirilo.androidsample.core.entities.Post
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(posts: List<Post>)

    @Query("SELECT * FROM post WHERE userId = :id")
    abstract fun getPostByUserId(id: Long): Flow<List<Post>>
}