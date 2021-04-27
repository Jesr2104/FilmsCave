package com.justjump.filmscave.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.justjump.filmscave.R
import com.justjump.filmscave.databinding.FragmentLoginBinding
import com.justjump.filmscave.users.viewmodel.LogInViewModel
import org.json.JSONObject

class LoginFragment : Fragment(),LogInViewModel.Message {

    companion object{
        const val GOOGLE_SIGN_IN = 100
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var logInViewModel: LogInViewModel
    private lateinit var navController: NavController
    private val callbackManager = CallbackManager.Factory.create()
    private val args: LoginFragmentArgs by navArgs()

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

        if (args.emailUser != "-1"){
            binding.dataEmail.setText(args.emailUser)
            binding.dataPassword.requestFocus()
        }

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

        binding.iconGoogle.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            getTokenGoogle()
        }

        binding.iconFacebook.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            getTokenFacebook()
        }

        // recover the password
        binding.forgotPassword.setOnClickListener {
            navController.navigate(R.id.action_login_to_recoverViaEmail)
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    // retrieve google user token
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SignUpFragment.GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) { logInViewModel.logInUserGoogle(this, requireContext(),account) }
            } catch (e: Exception) { Log.e("Jesr2104", e.message!!) }
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

    //********************************************************//
    //          Google Token
    //********************************************************//
    private fun getTokenGoogle(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, SignUpFragment.GOOGLE_SIGN_IN)
    }

    //********************************************************//
    //          Facebook Token
    //********************************************************//
    private fun getTokenFacebook(){
        LoginManager.getInstance().logOut()
        LoginManager.getInstance().logInWithReadPermissions(this@LoginFragment, listOf("email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    result?.let {
                        GraphRequest.newMeRequest(it.accessToken,
                            object : GraphRequest.GraphJSONObjectCallback {
                                override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                                    val email = `object`!!.get("email")
                                    logInViewModel.logInFacebook(this@LoginFragment, requireContext(), it.accessToken, email.toString())
                                }
                            }).apply {
                            val bundle = Bundle()
                            bundle.putString("fields","email")
                            parameters =  bundle
                        }.executeAsync()
                    }
                }
                override fun onCancel() {}
                override fun onError(error: FacebookException?) {
                    Toast.makeText(requireContext(), "${error!!.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}