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
import com.example.github_user.adapter.FollowersAdapter
import com.example.github_user.adapter.FollowingAdapter
import com.example.github_user.adapter.ListUserAdapter
import com.example.github_user.databinding.FragmentFollowBinding
import com.example.github_user.model.User
import com.example.github_user.viewmodel.FollowersViewModel
import com.example.github_user.viewmodel.FollowingViewModel

class FollowFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(index: Int, username: String?) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvFollow
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val username = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 1
        showLoading(true)
        when (index) {
            1 -> setFollowers(username)
            2 -> setFollowing(username)
        }
    }

    private fun setFollowers(username: String?) {
        followersAdapter = FollowersAdapter()
        recyclerView.adapter = followersAdapter
        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        followersViewModel.setUserFollowers(username!!)
        followersViewModel.getUserFollowers().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                followersAdapter.setData(users)
                showLoading(false)
            }
        })
        followersAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                showSelectedUser(user)
            }
        })
    }

    private fun setFollowing(username: String?) {
        followingAdapter = FollowingAdapter()
        recyclerView.adapter = followingAdapter
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.setUserFollowing(username!!)
        followingViewModel.getUserFollowing().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                followingAdapter.setData(users)
                showLoading(false)
            }
        })
        followingAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                showSelectedUser(user)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(context, user.username, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.progressBarFollow.visibility = View.VISIBLE
        else
            binding.progressBarFollow.visibility = View.INVISIBLE
    }
}