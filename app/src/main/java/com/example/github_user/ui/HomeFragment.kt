package com.example.github_user.ui

import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_user.R
import com.example.github_user.adapter.ListUserAdapter
import com.example.github_user.databinding.FragmentHomeBinding
import com.example.github_user.model.User

class HomeFragment : Fragment() {
    private lateinit var listUserAdapter: ListUserAdapter

    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataAvatar: TypedArray

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvGithub
        recyclerView.setHasFixedSize(true)

        prepare()
        addItem()

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        listUserAdapter = ListUserAdapter(list)
        recyclerView.adapter = listUserAdapter
    }

    private fun prepare() {
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollower = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(
                dataUsername[position],
                dataName[position],
                dataLocation[position],
                dataRepository[position].toInt(),
                dataCompany[position],
                dataFollower[position].toInt(),
                dataFollowing[position].toInt(),
                dataAvatar.getResourceId(position, -1),
            )
            list.add(user)
        }
    }
}