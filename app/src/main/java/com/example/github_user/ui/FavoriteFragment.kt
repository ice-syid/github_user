package com.example.github_user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_user.adapter.ListUserFavoriteAdapter
import com.example.github_user.databinding.FragmentFavoriteBinding
import com.example.github_user.model.User
import com.example.github_user.viewmodel.UserViewModel

class FavoriteFragment : Fragment() {
    private lateinit var listUserFavoriteAdapter: ListUserFavoriteAdapter
    private lateinit var userViewModel: UserViewModel

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showRecyclerList()
    }

    private fun showRecyclerList() {
        showLoading(true)
        recyclerView = binding.rvGithub
        recyclerView.setHasFixedSize(true)

        listUserFavoriteAdapter = ListUserFavoriteAdapter()
        listUserFavoriteAdapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = listUserFavoriteAdapter

        showLoading(true)
        userViewModel.readAllData.observe(viewLifecycleOwner, { listUsers ->
            if (listUsers != null) {
                listUserFavoriteAdapter.setData(listUsers as ArrayList<User>)
                showLoading(false)
            }
        })

        listUserFavoriteAdapter.setOnItemClickCallback(object :
            ListUserFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(context, user.username, Toast.LENGTH_SHORT).show()
    }
}