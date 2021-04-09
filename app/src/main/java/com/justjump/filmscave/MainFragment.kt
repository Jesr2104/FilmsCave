package com.justjump.filmscave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave._viewmodel.MainViewModel
import com.justjump.filmscave.databinding.FragmentMainBinding
import com.justjump.filmscave.framework.room.users.RoomDataSource

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel()::class.java)
        navController = view.findNavController()

        // this function check if the user is already login before.
        if (mainViewModel.checkPreviewSession(requireContext())){
            navController.navigate(R.id.action_mainActivity_to_homeFragment)
        }

        binding.anonymousUsers.setOnClickListener {
            mainViewModel.createAnonymousUser(requireContext(), RoomDataSource())
            navController.navigate(R.id.action_mainActivity_to_homeFragment)
        }

        binding.signUpButton.setOnClickListener {
            navController.navigate(R.id.action_mainActivity_to_signUp)
        }

        binding.logInButton.setOnClickListener {
            navController.navigate(R.id.action_mainActivity_to_login)
        }
    }
}