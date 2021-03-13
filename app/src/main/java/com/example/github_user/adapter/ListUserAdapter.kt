package com.example.github_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.github_user.R
import com.example.github_user.databinding.ItemUserBinding
import com.example.github_user.model.User
import com.example.github_user.ui.HomeFragmentDirections

class ListUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            binding.imgPhoto.setImageResource(user.avatar)
            binding.tvName.text = user.name
            binding.tvUsername.text = user.username
            binding.btnViewProfile.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(user)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserAdapter.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount() = listUser.size
}