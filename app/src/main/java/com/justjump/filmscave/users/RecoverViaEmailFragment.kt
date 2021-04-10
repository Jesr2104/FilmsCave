package com.justjump.filmscave.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.justjump.filmscave.databinding.FragmentRecoverViaEmailBinding

import com.justjump.filmscave.users.viewmodel.RecoverViaEmailViewModel

class RecoverViaEmailFragment : Fragment(), RecoverViaEmailViewModel.Message {

    private lateinit var binding: FragmentRecoverViaEmailBinding
    private lateinit var recoverViaEmailViewModel: RecoverViaEmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentRecoverViaEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        // initial the viewModel
        recoverViaEmailViewModel = ViewModelProvider(this).get(RecoverViaEmailViewModel()::class.java)

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
        TODO("Not yet implemented")
    }
}