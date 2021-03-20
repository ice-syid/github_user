package com.example.github_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_user.R
import com.example.github_user.databinding.ItemUserBinding
import com.example.github_user.model.User
import com.example.github_user.ui.HomeFragmentDirections

class ListUserAdapter(var listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            Glide.with(binding.root).load(user.avatar).into(binding.imgPhoto)
            binding.tvUsername.text = user.username
            binding.btnViewProfile.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(user)
                itemView.findNavController().navigate(action)
            }
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
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

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}