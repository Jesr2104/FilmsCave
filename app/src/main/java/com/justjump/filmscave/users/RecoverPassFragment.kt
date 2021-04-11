package com.justjump.filmscave.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave.databinding.FragmentRecoverPassBinding

import com.justjump.filmscave.users.viewmodel.RecoverPassViewModel

class RecoverPassFragment : Fragment() {

    private lateinit var binding: FragmentRecoverPassBinding
    private lateinit var recoverPassViewModel: RecoverPassViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentRecoverPassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        // initial the viewModel
        recoverPassViewModel = ViewModelProvider(this).get(RecoverPassViewModel()::class.java)
        navController = view.findNavController()

        binding.cardViaSms.setOnClickListener {
            navController.navigate(R.id.action_recoverPass_to_recoverViaSMS)
        }

        binding.cardViaEmail.setOnClickListener {
            navController.navigate(R.id.action_recoverPassFragment_to_recoverViaEmailFragment)
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}