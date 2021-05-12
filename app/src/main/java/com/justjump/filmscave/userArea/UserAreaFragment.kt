package com.justjump.filmscave.userArea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave._utils.changeColorStatusBar
import com.justjump.filmscave.databinding.FragmentUserAreaBinding
import com.justjump.filmscave.userArea.viewmodel.UserAreaViewModel

class UserAreaFragment : Fragment() {

    private lateinit var userAreaViewModel: UserAreaViewModel
    private lateinit var binding: FragmentUserAreaBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentUserAreaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeColorStatusBar(requireActivity(),R.color.main_dark_status_bar_color)

        // initial the viewModel
        userAreaViewModel = ViewModelProvider(this).get(UserAreaViewModel::class.java)
        navController = view.findNavController()

        val myObserverEmail = Observer<String> { binding.email.text = userAreaViewModel.emailValue.value }
        val myObserverUsername = Observer<String> { binding.username.text = userAreaViewModel.userNameValue.value }

        userAreaViewModel.emailValue.observe(viewLifecycleOwner,myObserverEmail)
        userAreaViewModel.userNameValue.observe(viewLifecycleOwner,myObserverUsername)

        userAreaViewModel.loadUserData(requireContext())

        binding.signOut.setOnClickListener {
            navController.navigate(R.id.action_userAreaFragment_to_mainActivity)
            userAreaViewModel.closeSession(requireContext())
        }

        // Lists
        binding.cardViewList.setOnClickListener {

        }

        // recommended
        binding.cardViewRecommended.setOnClickListener {

        }

        // Friends
        binding.cardViewFriends.setOnClickListener {
            navController.navigate(R.id.action_userAreaFragment_to_friendsFragment)
        }

        // Settings
        binding.cardViewSettings.setOnClickListener {

        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        changeColorStatusBar(requireActivity(),R.color.main_light_blue)
    }
}