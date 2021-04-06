package com.example.github_user.di

import android.content.Context
import com.example.github_user.data.LocalDataSource
import com.example.github_user.data.UserDatabase
import com.example.github_user.data.UserRepository

object Injection {
    fun provideInjection(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val localDataSource = LocalDataSource.getInstance(database.userDao())

        return UserRepository.getInstance(localDataSource)
    }
}