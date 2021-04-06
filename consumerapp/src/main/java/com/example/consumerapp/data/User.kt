package com.example.consumerapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val username: String?,
    val avatar: String?
) : Parcelable