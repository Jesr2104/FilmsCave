package com.justjump.filmscave.data.datasources.users.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data._interfaces.SignUpIDataSource
import com.justjump.filmscave.domain.users.UserValidationDataModel

class SignUpDataSource : SignUpIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()

    // this is the implementation of the signUp user on the server of data bases
    override fun signUp(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {

        // create user on fireBase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            userValidationDataModel.User.trim(),
            userValidationDataModel.Password.trim()
        )
            .addOnCompleteListener { task ->
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

    override fun signUpGoogle(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun signUpFacebook(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {
        TODO("Not yet implemented")
    }
}