package com.justjump.filmscave.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.justjump.filmscave.data.repositories.users.RemoteUserDataSource
import com.justjump.filmscave.domain.users.UserValidation

class UserDataSource: RemoteUserDataSource {

    private var successfullyUserCreate = false

    // this is the implementation of the signUp user on the server of data bases
    override fun signUp(user: UserValidation): Boolean {

        // create user on fireBase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.User.trim(), user.Password.trim())
            .addOnCompleteListener { task ->
                successfullyUserCreate = task.isSuccessful
            }
            .addOnFailureListener { task ->

            }
    }
}