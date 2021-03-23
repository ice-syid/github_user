package com.example.github_user.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user.BuildConfig
import com.example.github_user.model.UserDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val userDetail = MutableLiveData<UserDetail>()

    fun setUserDetail(username: String) {
        Log.d("test", "getDataUserDetail")
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ${BuildConfig.API_KEY}")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val username = responseObject.getString("login")
                    val name = responseObject.getString("name")
                    val location = responseObject.getString("location")
                    val repository = responseObject.getInt("public_repos")
                    val company = responseObject.getString("company")
                    val follower = responseObject.getInt("followers")
                    val following = responseObject.getInt("following")
                    val avatar = responseObject.getString("avatar_url")
                    val user = UserDetail(
                        username,
                        name,
                        location,
                        repository,
                        company,
                        follower,
                        following,
                        avatar
                    )
                    userDetail.postValue(user)
                } catch (e: Exception) {
                    Log.e("onSuccess", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.e("onFailure", errorMessage)
            }
        })
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail
}