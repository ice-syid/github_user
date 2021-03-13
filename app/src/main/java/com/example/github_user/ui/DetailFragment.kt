package com.example.github_user.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.github_user.R
import com.example.github_user.databinding.FragmentDetailBinding
import com.example.github_user.model.User


class DetailFragment : Fragment() {
    private lateinit var user: User

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

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
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgDetailPhoto.setImageResource(user.avatar)
        binding.tvDetailName.text = user.name
        binding.tvDetailCompany.text = user.company
        binding.tvDetailLocation.text = user.location
        binding.tvFollowerValue.text = user.follower.toString()
        binding.tvFollowingValue.text = user.following.toString()
        binding.tvRepositoryValue.text = user.repository.toString()

        binding.tvRepoImg.setImageResource(user.avatar)
        binding.tvRepoUsername.text = user.username

        binding.tvRepositoriesValue.text = user.repository.toString()
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
}