package com.justjump.filmscave.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave._utils.validatePassword
import com.justjump.filmscave.databinding.FragmentSignUpBinding
import com.justjump.filmscave.users.viewmodel.SignUpViewModel

class SignUpFragment : Fragment(), SignUpViewModel.Message {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpViewModel:SignUpViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initial the viewModel
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel()::class.java)
        navController = view.findNavController()

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
                            signUpViewModel.signUpUser(this, requireActivity().applicationContext)

                        } else {
                            Toast.makeText(requireContext(), getString(R.string.password_rules_6chars), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // message to tell to the user one of fields is empty
                    Toast.makeText(requireContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.accept_policies), Toast.LENGTH_SHORT).show()
            }
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showMessage(message: Int, success: Boolean) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
        binding.loading.visibility = View.INVISIBLE

        if (success){
            navController.navigate(R.id.action_signUp_to_homeFragment)
        }
    }
}