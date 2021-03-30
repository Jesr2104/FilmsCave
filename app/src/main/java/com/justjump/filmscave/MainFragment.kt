package com.justjump.filmscave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.databinding.FragmentStartApplicationBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentStartApplicationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentStartApplicationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        binding.signUpButton.setOnClickListener {
            navController.navigate(R.id.action_startApplication_to_signUp)
        }

        binding.logInButton.setOnClickListener {
            navController.navigate(R.id.action_startApplication_to_login)
        }
    }
}