package com.example.github_user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user.api.ApiRequest
import com.example.github_user.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class FollowViewModel : ViewModel() {
    private val listFollow = MutableLiveData<ArrayList<User>>()

    fun setUserFollow(username: String, option: Int) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = when (option) {
                0 -> api.getFollowersUser(username).awaitResponse()
                else -> api.getFollowingUser(username).awaitResponse()
            }
            if (response.isSuccessful) {
                val data = response.body()
                listFollow.postValue(data as ArrayList<User>?)
            }
        }
    }

    fun getUserFollow(): LiveData<ArrayList<User>> = listFollow
}