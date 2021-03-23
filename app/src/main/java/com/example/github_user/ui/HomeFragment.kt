package com.example.github_user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_user.R
import com.example.github_user.adapter.ListUserAdapter
import com.example.github_user.databinding.FragmentHomeBinding
import com.example.github_user.model.User
import com.example.github_user.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showRecyclerList()
        setupSearchListener()
    }

    private fun showRecyclerList() {
        recyclerView = binding.rvGithub
        recyclerView.setHasFixedSize(true)

        listUserAdapter = ListUserAdapter()
        listUserAdapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = listUserAdapter

        showLoading(true)
        homeViewModel.setListUser()
        homeViewModel.getUsers().observe(viewLifecycleOwner, { listUsers ->
            if (listUsers != null) {
                listUserAdapter.setData(listUsers)
                showLoading(false)
            }
        })

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(context, user.username, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setupSearchListener() {
        val searchView = binding.searchView

        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                homeViewModel.setListUserSearch(query)
                homeViewModel.getUsers().observe(viewLifecycleOwner, { listUsers ->
                    if (listUsers != null) {
                        listUserAdapter.setData(listUsers)
                        showLoading(false)
                    }
                })
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                return false
            }
        })
    }
}