package com.example.github_user.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.github_user.R
import com.example.github_user.data.UserDao
import com.example.github_user.data.UserDatabase
import com.example.github_user.model.User

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val favoriteUsers = mutableListOf<User>()
    private var userDao: UserDao? = null

    override fun onCreate() {
        userDao = UserDatabase.getDatabase(mContext).userDao()
    }

    override fun onDataSetChanged() {
        val listUsers = userDao?.getUserFavoriteWidget()
        listUsers?.let { users ->
            favoriteUsers.addAll(users)
        }
    }

    override fun onDestroy() {
        userDao = null
    }

    override fun getCount(): Int = favoriteUsers.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(favoriteUsers[position].avatar)
                .submit()
                .get()

            rv.setImageViewBitmap(R.id.img_avatar, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extras = bundleOf(
            ImagesBannerWidget.EXTRA_ITEM to favoriteUsers[position].username
        )

        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.img_avatar, fillIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}