package com.example.github_user.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.github_user.model.User

class LocalDataSource(private val userDao: UserDao) {
    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(userDao: UserDao): LocalDataSource =
            instance ?: LocalDataSource(userDao)
    }

    suspend fun addUser(user: User) = userDao.addUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    fun readAllData(): LiveData<List<User>> = userDao.readAllData()

    fun readAllDataCursor(): Cursor = userDao.readAllDataCursor()
}