package com.justjump.filmscave.data.datasources.users.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.justjump.filmscave.data._interfaces.RoomFramework
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data._interfaces.SignUpIDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel

class SignUpDataSource(private val roomFramework: RoomFramework) : SignUpIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()

    // this is the implementation of the signUp user on the server of data bases
    override fun signUp( appContext: Context, userValidationDataModel: UserValidationDataModel,
        userStructureDataModel: UserStructureDataModel ): LiveData<Resource<String>> {

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
                    //if the user is create in room correctly we send back the data
                    if (roomFramework.insertNewUser(appContext,userStructureDataModel)){
                        //TODO if missing as well create the user on firebase service

                        messageCreateUser.value = Resource.success()
                    }
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