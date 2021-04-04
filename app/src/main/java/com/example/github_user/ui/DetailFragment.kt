package com.example.github_user.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github_user.R
import com.example.github_user.adapter.SectionsPagerAdapter
import com.example.github_user.databinding.FragmentDetailBinding
import com.example.github_user.model.User
import com.example.github_user.model.UserDetail
import com.example.github_user.viewmodel.DetailViewModel
import com.example.github_user.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private lateinit var user: User
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userViewModel: UserViewModel

    private var isFavorite: Boolean = false
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        user = args.user
        (activity as AppCompatActivity?)?.supportActionBar?.title = user.username
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUserDetail(user)
        setupTab()
        setupFavorite()

        binding.isFavorite.setOnClickListener {
            if (isFavorite) {
                deleteData()
            } else {
                insertData()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val baseUrl = "https://github.com/"
        val url = baseUrl + user.username

        return when (item.itemId) {
            R.id.action_share -> {
                val action = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_TEXT, "$user")
                    this.type = "text/plain"
                }
                startActivity(action)
                return true
            }
            R.id.action_open_browser -> {
                val action = Intent().apply {
                    this.action = Intent.ACTION_VIEW
                    this.data = Uri.parse(url)
                }
                startActivity(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupUserDetail(user: User) {
        showLoading(true)
        user.username?.let { detailViewModel.setUserDetail(it) }
        detailViewModel.getUserDetail().observe(viewLifecycleOwner, { detailUser ->
            setupView(detailUser)
        })
        showLoading(false)
    }

    private fun setupView(userDetail: UserDetail) {
        Glide.with(binding.root).load(userDetail.avatar).into(binding.imgDetailPhoto)
        binding.tvDetailName.text = userDetail.name
        binding.tvDetailCompany.text = userDetail.company
        binding.tvDetailLocation.text = userDetail.location
        binding.tvFollowerValue.text = userDetail.followers.toString()
        binding.tvFollowingValue.text = userDetail.following.toString()
        binding.tvRepositoryValue.text = userDetail.repository.toString()
    }

    private fun setupTab() {
        val adapter = SectionsPagerAdapter(requireActivity() as AppCompatActivity)
        adapter.username = user.username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = adapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        (activity as AppCompatActivity?)?.supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.progressBarDetail.visibility = View.VISIBLE
        else
            binding.progressBarDetail.visibility = View.INVISIBLE
    }

    private fun setupFavorite() {
        userViewModel.readAllData.observe(viewLifecycleOwner, { userFav ->
            for (item in userFav) {
                Log.d("syid", item.username.toString() + "|" + user.username)
                if (item.username.equals(user.username))
                    isFavorite = true
                favoriteState(isFavorite)
            }
        })
    }

    private fun favoriteState(state: Boolean) {
        context?.let { it ->
            if (state)
                binding.isFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_favorite_on
                    )
                )
            else {
                binding.isFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_favorite_off
                    )
                )
            }
        }
    }

    private fun insertData() {
        Log.d("syid", "insert")
        isFavorite = !isFavorite
        userViewModel.addUser(User(user.id, user.username, user.avatar))
        favoriteState(isFavorite)
    }

    private fun deleteData() {
        Log.d("syid", "delete")
        isFavorite = !isFavorite
        userViewModel.deleteUser(user)
        favoriteState(isFavorite)
    }
}
