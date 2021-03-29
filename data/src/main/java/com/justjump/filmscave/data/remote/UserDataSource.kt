package com.justjump.filmscave.data.remote

import Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.justjump.filmscave.data.repositories.users.RemoteUserDataSource
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
}