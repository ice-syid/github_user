package com.example.github_user.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable