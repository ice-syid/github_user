package com.example.github_user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user.api.ApiRequest
import com.example.github_user.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class DetailViewModel : ViewModel() {
    private val userDetail = MutableLiveData<UserDetail>()

    fun setUserDetail(username: String) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getDetailUser(username).awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()
                userDetail.postValue(data)
            }
        }
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail
}