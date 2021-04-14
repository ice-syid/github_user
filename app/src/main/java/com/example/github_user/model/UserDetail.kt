package com.example.github_user.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("login")
    val username: String,
    val name: String,
    val location: String,
    @SerializedName("public_repos")
    val repository: Int,
    val company: String,
    val followers: Int,
    val following: Int,
    @SerializedName("avatar_url")
    val avatar: String
)