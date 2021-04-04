package com.example.github_user.api

import com.example.github_user.BuildConfig
import com.example.github_user.model.User
import com.example.github_user.model.UserDetail
import com.example.github_user.model.UserSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {
    @GET("/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUsers(): Call<List<User>>

    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getSearchUsers(@Query("q") q: String): Call<UserSearch>

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetail>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowersUser(@Path("username") username: String): Call<List<User>>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowingUser(@Path("username") username: String): Call<List<User>>
}