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
import com.example.github_user.adapter.FollowAdapter
import com.example.github_user.adapter.ListUserAdapter
import com.example.github_user.databinding.FragmentFollowBinding
import com.example.github_user.model.User
import com.example.github_user.viewmodel.FollowViewModel

class FollowFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var followAdapter: FollowAdapter
    private lateinit var followViewModel: FollowViewModel

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding as FragmentFollowBinding

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
            1 -> setFollow(username, 0)
            2 -> setFollow(username, 1)
        }
    }

    private fun setFollow(username: String?, option: Int) {
        followAdapter = FollowAdapter()
        recyclerView.adapter = followAdapter
        followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)
        username?.let { followViewModel.setUserFollow(it, option) }
        followViewModel.getUserFollow().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                followAdapter.setData(users)
                showLoading(false)
            }
        })
        followAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                Toast.makeText(context, user.username, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.progressBarFollow.visibility = View.VISIBLE
        else
            binding.progressBarFollow.visibility = View.INVISIBLE
    }
}