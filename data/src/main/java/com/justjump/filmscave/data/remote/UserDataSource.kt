package com.justjump.filmscave.data.remote

import com.justjump.filmscave.data.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.justjump.filmscave.data.interfaces.RemoteUserDataSource
import com.justjump.filmscave.domain.users.UserValidation

class UserDataSource: RemoteUserDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()

    // this is the implementation of the signUp user on the server of data bases
    override fun signUp(user: UserValidation): LiveData<Resource<String>> {

        // create user on fireBase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            user.User.trim(),
            user.Password.trim()
        )
            .addOnCompleteListener{ task ->
                task.addOnFailureListener {
                    val errorCode = (task.exception as FirebaseAuthException?)!!.errorCode
                    messageCreateUser.value = Resource.error(errorCode)
                }

                task.addOnSuccessListener {
                    messageCreateUser.value = Resource.success()
                }
            }
    return messageCreateUser
    }

//    override fun signUpGoogle(user: UserValidation): LiveData<Resource<String>> {
//        val googleConfig: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("872848905940-7o570ak5jvm1egtanstlm8rq6aqlta4s.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//        val googleClient: GoogleSignInClient = GoogleSignIn.getClient(this,googleConfig)
//
//    }
}