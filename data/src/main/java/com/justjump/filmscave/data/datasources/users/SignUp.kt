package com.justjump.filmscave.data.datasources.users

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.data._interfaces.SignUpIDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseAuthDataSource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUp(private val roomFrameworkDataSource: RoomFrameworkDataSource) : SignUpIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()
    private val usersFirebase = UsersFirebaseDataSource()
    private val usersFirebaseAuth = UsersFirebaseAuthDataSource()

    // this is the implementation of the signUp user with email and password
    override fun signUp(appContext: Context, userValidationDataModel: UserValidationDataModel,userStructureDataModel: UserStructureDataModel
    ): LiveData<Resource<String>> {

        GlobalScope.launch(Dispatchers.Main) {
            // check if the username is able to used
            if (usersFirebase.checkUserName(userStructureDataModel.userName)){
                /* Firebase Auth */
                val result = usersFirebaseAuth.signUpUser(userValidationDataModel)
                if (result.status){
                    if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(
                            appContext,
                            userStructureDataModel
                        ) &&
                        /* Firebase Cloud FireStore => */ usersFirebase.insertUser(
                            userStructureDataModel
                        )) { messageCreateUser.value = Resource.success() }
                } else { messageCreateUser.value = Resource.error(result.codeException)}
            } else { messageCreateUser.value = Resource.error("ERROR_USERNAME_NOT_ABLE") }
        }
        return messageCreateUser
    }

    // this is the implementation of the signUp user with google
    override fun signUpGoogle(appContext: Context, account: GoogleSignInAccount, userStructureDataModel: UserStructureDataModel
    ): LiveData<Resource<String>> {

        GlobalScope.launch(Dispatchers.Main) {
            if (usersFirebaseAuth.checkEmail(userStructureDataModel.email)){
                val result = usersFirebaseAuth.signUpUserGoogle(account)
                if (result.status){
                    if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(
                        appContext,
                        userStructureDataModel
                    ) &&
                    /* Firebase Cloud FireStore => */ usersFirebase.insertUser(
                        userStructureDataModel
                    )) { messageCreateUser.value = Resource.success() }
                } else { messageCreateUser.value = Resource.error(result.codeException) }
            } else { messageCreateUser.value = Resource.error("ERROR_EMAIL_ALREADY_IN_USE") }
        }
        return messageCreateUser
    }

    override fun signUpFacebook(appContext: Context, token: AccessToken, emailFromTokenFacebook: String, userStructureDataModel: UserStructureDataModel
    ): LiveData<Resource<String>> {

        GlobalScope.launch(Dispatchers.Main) {
            if (usersFirebaseAuth.checkEmail(emailFromTokenFacebook)){
                val result = usersFirebaseAuth.signUpUserFacebook(token)

                if (result["email"]!!.isNotEmpty()){
                    val newUser = UserStructureDataModel(
                        email = result["email"].toString(),
                        userName = result["email"].toString(),
                        avatar = result["avatar"].toString() )

                    /* Room Active Session => */
                    if (roomFrameworkDataSource.insertNewUser(appContext,newUser) &&
                        /* Firebase Cloud FireStore => */
                        usersFirebase.insertUser(newUser)) {
                            messageCreateUser.value = Resource.success()
                        }
                }
            } else { messageCreateUser.value = Resource.error("ERROR_EMAIL_ALREADY_IN_USE") }
        }
        return messageCreateUser
    }
}