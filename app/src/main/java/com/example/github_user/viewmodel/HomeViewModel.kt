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

const val BASE_URL = "https://api.github.com"

class HomeViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setListUser() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getUsers().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()
                listUsers.postValue(data as ArrayList<User>?)
            }
        }
    }

    fun setListUserSearch(username: String) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val listData = ArrayList<User>()
            val response = api.getSearchUsers(username).awaitResponse()
            val users = response.body()?.items
            if (users != null) {
                for (i in 0 until (users.size)) {
                    val user = users[i]
                    listData.add(User(user.id, user.username, user.avatar))
                }
                listUsers.postValue(listData)
            }
        }
    }

    fun getUsers(): LiveData<ArrayList<User>> = listUsers
}