package com.example.github_user.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github_user.BuildConfig
import com.example.github_user.R
import com.example.github_user.adapter.SectionsPagerAdapter
import com.example.github_user.databinding.FragmentDetailBinding
import com.example.github_user.model.User
import com.example.github_user.model.UserDetail
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailFragment : Fragment() {
    private lateinit var user: User
    private lateinit var userDetail: UserDetail

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = user.username
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getDataUserDetail(user.username)
        setupTab()
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
                    this.putExtra(Intent.EXTRA_TEXT, "${user}")
                    this.type = "text/plain"
                }
                startActivity(action)
                return true
            }
            R.id.action_open_browser -> {
                val action = Intent().apply {
                    this.action = Intent.ACTION_VIEW
                    this.setData(Uri.parse(url))
                }
                startActivity(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView(userDetail: UserDetail) {
        Glide.with(binding.root).load(userDetail.avatar).into(binding.imgDetailPhoto)
        binding.tvDetailName.text = userDetail.name
        binding.tvDetailCompany.text = userDetail.company
        binding.tvDetailLocation.text = userDetail.location
        binding.tvFollowerValue.text = userDetail.follower.toString()
        binding.tvFollowingValue.text = userDetail.following.toString()
        binding.tvRepositoryValue.text = userDetail.repository.toString()
    }

    private fun setupTab() {
        val adapter = SectionsPagerAdapter(requireActivity() as AppCompatActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = adapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        (activity as AppCompatActivity?)!!.supportActionBar!!.elevation = 0f
    }

    private fun getDataUserDetail(username: String) {
        binding.progressBarDetail.visibility = View.VISIBLE
        Log.d("test", "getDataUserDetail")
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ${BuildConfig.API_KEY}")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val username = responseObject.getString("login")
                    val name = responseObject.getString("name")
                    val location = responseObject.getString("location")
                    val repository = responseObject.getInt("public_repos")
                    val company = responseObject.getString("company")
                    val follower = responseObject.getInt("followers")
                    val following = responseObject.getInt("following")
                    val avatar = responseObject.getString("avatar_url")
                    userDetail = UserDetail(
                        username,
                        name,
                        location,
                        repository,
                        company,
                        follower,
                        following,
                        avatar
                    )
                    binding.progressBarDetail.visibility = View.INVISIBLE
                    setupView(userDetail)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}