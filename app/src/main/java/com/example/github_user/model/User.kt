package com.example.github_user.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_favorite")
data class User(
    @PrimaryKey
    val id: Int,
    @SerializedName("login")
    val username: String?,
    @SerializedName("avatar_url")
    val avatar: String?
) : Parcelable