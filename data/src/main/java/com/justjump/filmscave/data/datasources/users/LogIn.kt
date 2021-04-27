package com.justjump.filmscave.data.datasources.users

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.justjump.filmscave.data._interfaces.LogInIDataSource
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseAuthDataSource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogIn(private val roomFrameworkDataSource: RoomFrameworkDataSource): LogInIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()
    private val usersFirebase = UsersFirebaseDataSource()
    private val usersFirebaseAuth = UsersFirebaseAuthDataSource()

    override fun logIn( appContext: Context, userValidationDataModel: UserValidationDataModel
        ): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {
            val result = usersFirebaseAuth.logInUser(userValidationDataModel)
            if (result.status){
                if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(appContext,
                    /* Firebase Cloud FireStore => */ usersFirebase.getUser(userValidationDataModel.email))){
                    messageCreateUser.value = Resource.success()
                }
            } else {messageCreateUser.value = Resource.error(result.codeException)}
        }
        return messageCreateUser
    }

    override fun logInGoogle(appContext: Context,account: GoogleSignInAccount
        ): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {
            if (!usersFirebaseAuth.checkEmail(account.email.toString())){
                val result = usersFirebaseAuth.signUpUserGoogle(account)
                if (result.status){
                    if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(appContext,
                        /* Firebase Cloud FireStore => */ usersFirebase.getUser(account.email.toString()))){
                        messageCreateUser.value = Resource.success()
                    }
                } else {Log.e("Jesr2104","the user has not been logged in correctly")}
            } else {
                messageCreateUser.value = Resource.error("ERROR_USER_IS_NOT_REGISTERED")
            }
        }
        return messageCreateUser
    }

    override fun logInFacebook(appContext: Context,token: AccessToken,emailFromTokenFacebook: String
        ): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {
            if (!usersFirebaseAuth.checkEmail(emailFromTokenFacebook)){
                val result = usersFirebaseAuth.signUpUserFacebook(token)
                if (result["email"] != ""){
                    if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(appContext,
                        /* Firebase Cloud FireStore => */ usersFirebase.getUser(result["email"].toString()))){
                        messageCreateUser.value = Resource.success()
                    }
                } else {Log.e("Jesr2104","the user has not been logged in correctly")}
            } else {
                messageCreateUser.value = Resource.error("ERROR_USER_IS_NOT_REGISTERED")
            }
        }
        return messageCreateUser
    }
}