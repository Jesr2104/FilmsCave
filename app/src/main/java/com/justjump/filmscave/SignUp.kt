package com.justjump.filmscave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.justjump.filmscave.data.remote.UserDataSource
import com.justjump.filmscave.data.repositories.users.UsersRepository
import com.justjump.filmscave.databinding.FragmentSignUpBinding
import com.justjump.filmscave.domain.users.UserValidation
import com.justjump.filmscave.usecases.SignUpUser

class SignUp : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding.buttonSingUp.setOnClickListener {

            // usecases call

            if(binding.dataEmail.text!!.isNotEmpty() && binding.dataPassword.text!!.isNotEmpty()){

                val newUser = UserValidation(
                    binding.dataEmail.text.toString(),
                    binding.dataPassword.text.toString()
                )

                val result = SignUpUser(UsersRepository(
                    UserDataSource()
                )).invoke(newUser)

                if (result){
                    Toast.makeText(requireContext(), "has been created successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "An error has occurred", Toast.LENGTH_SHORT).show()
                }

            } else {
                // massage to tell to de user one of fields is empty
            }
        }

        return binding.root
    }
}