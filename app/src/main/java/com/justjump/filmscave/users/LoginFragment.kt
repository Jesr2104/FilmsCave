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
            } else {
                // message to tell to the user one of fields is empty
                Toast.makeText(requireContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
            }
        }

        // recover the password
        binding.forgotPassword.setOnClickListener {
            navController.navigate(R.id.action_login_to_recoverPassFragment)
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showMessage(message: Int, success: Boolean, fieldError: Int) {
        binding.loading.visibility = View.INVISIBLE

        when(fieldError){
            1 -> {
                // email
                binding.dataEmail.requestFocus()
                binding.dataEmail.error = getString(message)
            }
            2 -> {
                // password
                binding.dataPassword.requestFocus()
                binding.dataPassword.error = getString(message)
            }
            else -> { Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show() }
        }
        if (success){ navController.navigate(R.id.action_login_to_homeFragment) }
    }
}