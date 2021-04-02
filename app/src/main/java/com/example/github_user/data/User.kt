package com.example.github_user.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorite")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val avatar: String
)