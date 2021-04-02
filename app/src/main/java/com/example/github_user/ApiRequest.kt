package com.example.github_user

import com.example.github_user.model.User
import com.example.github_user.model.UserSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiRequest {
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("/users")
    fun getUsers(): Call<List<User>>

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("/search/users")
    fun getSearchUsers(@Query("q") q: String): Call<UserSearch>
}