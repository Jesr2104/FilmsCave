package com.justjump.filmscave.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.justjump.filmscave.databinding.FragmentRecoverPassBinding
import com.justjump.filmscave.users.viewmodel.RecoverPassViewModel

class RecoverPassFragment : Fragment() {

    private lateinit var binding: FragmentRecoverPassBinding
    private lateinit var recoverPassViewModel: RecoverPassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        binding = FragmentRecoverPassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        // initial the viewModel
        recoverPassViewModel = ViewModelProvider(this).get(RecoverPassViewModel()::class.java)

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}