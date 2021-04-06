package com.example.github_user.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.github_user.model.User

class UserRepository(
    private val localDataSource: LocalDataSource
) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            localDataSource: LocalDataSource
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(localDataSource)
            }
    }

    val readAllData: LiveData<List<User>> = localDataSource.readAllData()

    suspend fun addUser(user: User) {
        localDataSource.addUser(user)
    }

    suspend fun deleteUser(user: User) {
        localDataSource.deleteUser(user)
    }

    fun readAllDataCursor(): Cursor = localDataSource.readAllDataCursor()
}