package com.justjump.filmscave.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave.databinding.FragmentLoginBinding
import com.justjump.filmscave.users.viewmodel.LogInViewModel

class LoginFragment : Fragment(),LogInViewModel.Message {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var logInViewModel: LogInViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initial the viewModel
        logInViewModel = ViewModelProvider(this).get(LogInViewModel()::class.java)
        navController = view.findNavController()

        //********************************************************//
        //          Event of logIn User
        //********************************************************//
        binding.buttonLogIn.setOnClickListener {
            if (binding.dataEmail.text!!.isNotEmpty() && binding.dataPassword.text!!.isNotEmpty()){
                logInViewModel.emailValue.value = binding.dataEmail.text.toString()
                logInViewModel.passwordValue.value = binding.dataPassword.text.toString()

                binding.loading.visibility = View.VISIBLE
                logInViewModel.logInUser(this,requireActivity().applicationContext)
            }
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showMessage(message: String, success: Boolean) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.loading.visibility = View.INVISIBLE

        if (success){
            navController.navigate(R.id.action_login_to_homeFragment)
        }
    }
}