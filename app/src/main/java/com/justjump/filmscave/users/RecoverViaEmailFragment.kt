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
import com.justjump.filmscave.databinding.FragmentRecoverViaEmailBinding

import com.justjump.filmscave.users.viewmodel.RecoverViaEmailViewModel

class RecoverViaEmailFragment : Fragment(), RecoverViaEmailViewModel.Message {

    private lateinit var binding: FragmentRecoverViaEmailBinding
    private lateinit var recoverViaEmailViewModel: RecoverViaEmailViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentRecoverViaEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        // initial the viewModel
        recoverViaEmailViewModel = ViewModelProvider(this).get(RecoverViaEmailViewModel()::class.java)
        navController = view.findNavController()

        //********************************************************//
        //          Event of reset password
        //********************************************************//
        binding.buttonSubmit.setOnClickListener {
            if (binding.dataEmail.text!!.isNotEmpty()){
                recoverViaEmailViewModel.emailValue.value = binding.dataEmail.text.toString()
                binding.loading.visibility = View.VISIBLE
                recoverViaEmailViewModel.resetPassword(this)
            }
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
            else -> { Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show() }
        }
        if (success){
            val action = RecoverViaEmailFragmentDirections.actionRecoverViaEmailToLogin(binding.dataEmail.text!!.toString())
            navController.navigate(action)
        }
    }
}