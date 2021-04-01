package com.justjump.filmscave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.justjump.filmscave.data.datasources.users.local.GetUserLogin
import com.justjump.filmscave.databinding.FragmentHomeBinding
import com.justjump.filmscave.framework.room.users.RoomDataSource

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        var data = GetUserLogin(RoomDataSource()).getUser(requireContext())

        binding.username.text = data.userName
        binding.email.text = data.email

        return binding.root
    }
}