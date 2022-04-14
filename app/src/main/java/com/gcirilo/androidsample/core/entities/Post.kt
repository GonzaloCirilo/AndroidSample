package com.gcirilo.androidsample.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Post(
    @PrimaryKey
    var id: Long,
    var userId: Long,
    var body: String,
    var title: String,
): Serializable
