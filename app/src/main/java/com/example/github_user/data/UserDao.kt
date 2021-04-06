package com.example.github_user.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.github_user.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_favorite ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_favorite ORDER BY id ASC")
    fun readAllDataCursor(): Cursor

    @Query("SELECT * FROM user_favorite")
    fun getUserFavoriteWidget(): List<User>
}