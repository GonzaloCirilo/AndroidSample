package com.gcirilo.androidsample.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var phone: String = "",
    var email: String = "",
): Serializable