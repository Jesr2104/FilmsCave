package com.justjump.filmscave.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.justjump.filmscave.R
import com.justjump.filmscave._utils.validatePassword
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.users.SignUp
import com.justjump.filmscave.databinding.FragmentSignUpBinding
import com.justjump.filmscave.framework.room.users.RoomDataSource
import com.justjump.filmscave.users.viewmodel.SignUpViewModel

class SignUpFragment : Fragment(), SignUpViewModel.Message {

    companion object{
        const val GOOGLE_SIGN_IN = 100
    }

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
                    if (binding.dataPassword.text.toString().validatePassword()){
                        signUpViewModel.emailValue.value = binding.dataEmail.text.toString()
                        signUpViewModel.passwordValue.value = binding.dataPassword.text.toString()

                        binding.loading.visibility = View.VISIBLE
                        signUpViewModel.signUpUser(this, requireActivity().applicationContext)
                    } else {
                        binding.dataPassword.requestFocus()
                        binding.dataPassword.error = getString(R.string.password_rules_6chars)
                    }
                } else {
                    // message to tell to the user one of fields is empty
                    Toast.makeText(requireContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.accept_policies), Toast.LENGTH_SHORT).show()
            }
        }

        binding.iconGoogle.setOnClickListener {
            getTokenGoogle()
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
                // Username
                binding.dataUserName.requestFocus()
                binding.dataUserName.error = getString(message)
            }
            2 -> {
                // Email
                binding.dataEmail.requestFocus()
                binding.dataEmail.error = getString(message)
            }
            else -> { Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show() }
        }
        if (success){ navController.navigate(R.id.action_signUp_to_homeFragment) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            if (account != null){
                SignUp(RoomDataSource()).signUpGoogle(account).observeForever{
                    when (it.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(requireContext(), "correctamente", Toast.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), it.codeException, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    //********************************************************//
    //          Google Token
    //********************************************************//
    private fun getTokenGoogle(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    //********************************************************//
    //          Facebook Token
    //********************************************************//
    private fun getTokenFacebook(){

    }
}