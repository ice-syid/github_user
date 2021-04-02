package com.example.github_user.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("login")
    val username: String? = null,
    @SerializedName("avatar_url")
    val avatar: String? = null
) : Parcelable