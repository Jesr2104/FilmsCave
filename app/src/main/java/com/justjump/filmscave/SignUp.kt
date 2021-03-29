package com.justjump.filmscave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.justjump.filmscave.databinding.FragmentSignUpBinding
import com.justjump.filmscave.utils.validatePassword
import com.justjump.filmscave.viewmodel.SignUpViewModel

class SignUp : Fragment(), SignUpViewModel.Message {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpViewModel:SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater)

        // initial the viewModel
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel()::class.java)

        //********************************************************//
        //          Event of SignUp User
        //********************************************************//
        binding.buttonSingUp.setOnClickListener{
            if (binding.switchAcceptPolicies.isChecked){
                if (binding.dataEmail.text!!.isNotEmpty() && binding.dataPassword.text!!.isNotEmpty() && binding.dataUserName.text!!.isNotEmpty()){
                    signUpViewModel.userNameValue.value = binding.dataUserName.text.toString()
                    if(signUpViewModel.validateUsername()){
                        if (binding.dataPassword.text.toString().validatePassword()){
                            signUpViewModel.emailValue.value = binding.dataEmail.text.toString()
                            signUpViewModel.passwordValue.value = binding.dataPassword.text.toString()

                            binding.loading.visibility = View.VISIBLE
                            signUpViewModel.signUpUser(this)
                        } else {
                            Toast.makeText(requireContext(), "The password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // message to tell to the user one of fields is empty
                    Toast.makeText(requireContext(), "Some required fields are empty", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "You must accept the policies before proceeding", Toast.LENGTH_SHORT).show()
            }
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.loading.visibility = View.INVISIBLE
    }
}