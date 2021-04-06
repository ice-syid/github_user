package com.example.consumerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consumerapp.R
import com.example.consumerapp.data.User
import com.example.consumerapp.databinding.ItemUserBinding

class UserFavoriteAdapter(private val mContext: Context) :
    RecyclerView.Adapter<UserFavoriteAdapter.ListViewHolder>() {

    private var listUser: MutableList<User> = mutableListOf()

    fun setData(users: MutableList<User>) {
        this.listUser = users
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            Glide.with(binding.root).load(user.avatar).into(binding.imgPhoto)
            binding.tvUsername.text = user.username
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, user.username, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}