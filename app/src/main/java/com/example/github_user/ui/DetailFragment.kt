package com.example.github_user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.github_user.databinding.FragmentDetailBinding
import com.example.github_user.model.User


class DetailFragment : Fragment() {
    private lateinit var user: User

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.tvDetailName.setText(user.name)
        binding.tvDetailCompany.setText(user.company)
        binding.tvDetailLocation.setText(user.location)
        binding.tvFollowerValue.setText(user.follower.toString())
        binding.tvFollowingValue.setText(user.following.toString())
        binding.tvRepositoryValue.setText(user.repository.toString())
    }
}