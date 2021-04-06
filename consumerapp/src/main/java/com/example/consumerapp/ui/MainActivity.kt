package com.example.consumerapp.ui

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.adapter.UserFavoriteAdapter
import com.example.consumerapp.data.User
import com.example.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val userFavoriteAdapter: UserFavoriteAdapter by lazy {
        UserFavoriteAdapter(this)
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getDataContentProvider()
    }

    private fun getDataContentProvider() {
        val AUTHORITY = "com.example.github_user"
        val TABLE_NAME = "user_favorite"

        val uri: Uri = Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        initAdapter(cursor)
    }

    private fun initAdapter(cursor: Cursor?) {
        val userFavoriteList = ArrayList<User>()

        binding.rvGithub.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userFavoriteAdapter
        }
        cursor?.let {
            while (it.moveToNext()) {
                userFavoriteList.add(
                    User(
                        it.getString(it.getColumnIndex(it.getColumnName(1))),
                        it.getString(it.getColumnIndex(it.getColumnName(2)))
                    )
                )
            }
            userFavoriteAdapter.setData(userFavoriteList)
        }
    }
}