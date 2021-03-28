package com.justjump.filmscave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.justjump.filmscave.databinding.FragmentSignUpBinding
import com.justjump.filmscave.viewmodel.SignUpViewModel

class SignUp : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpViewModel:SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater)

        // initial the viewModel
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        //********************************************************//
        //          Event of SignUp User
        //********************************************************//
        binding.buttonSingUp.setOnClickListener{
            if (binding.dataEmail.text!!.isNotEmpty() && binding.dataPassword.text!!.isNotEmpty()){
                signUpViewModel.userValue.value = binding.dataName.text.toString()
                signUpViewModel.passwordValue.value = binding.dataPassword.text.toString()
                signUpViewModel.signUpUser()
            } else {
                // massage to tell to de user one of fields is empty
                Toast.makeText(requireContext(), "Some required fields are empty", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}