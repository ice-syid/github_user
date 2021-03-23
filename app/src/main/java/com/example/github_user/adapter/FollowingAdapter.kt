package com.example.github_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_user.R
import com.example.github_user.databinding.ItemUserBinding
import com.example.github_user.model.User

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {
    private var listFollowing = ArrayList<User>()
    private lateinit var onItemClickCallback: ListUserAdapter.OnItemClickCallback

    fun setData(users: ArrayList<User>) {
        listFollowing.clear()
        listFollowing.addAll(users)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: User) {
            Glide.with(binding.root).load(user.avatar).into(binding.imgPhoto)
            binding.tvUsername.text = user.username
//            binding.btnViewProfile.setOnClickListener {
//                val action =
//                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(user)
//                itemView.findNavController().navigate(action)
//            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFollowing.size
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ListViewHolder, position: Int) {
        val user = listFollowing[position]
        holder.bind(user)
    }

    fun setOnItemClickCallback(onItemClickCallback: ListUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}