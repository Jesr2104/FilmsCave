package com.justjump.filmscave.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.SignUp
import com.justjump.filmscave.data.remote.UserDataSource
import com.justjump.filmscave.data.repositories.users.UsersRepository
import com.justjump.filmscave.data.utils.Status
import com.justjump.filmscave.domain.users.UserValidation
import com.justjump.filmscave.usecases.SignUpUser

class SignUpViewModel() : ViewModel() {

    companion object{
        var ID0_MESSAGE = "The user has been created successfully."
        var ID1_MESSAGE = "The mail address is badly formatted."
        var ID2_MESSAGE = "The mail address is already in use by another account."
    }

    interface Message{
        fun showMessage(message: String)
    }

    var userNameValue = MutableLiveData<String>()
    var emailValue = MutableLiveData<String>()
    var passwordValue = MutableLiveData<String>()

    fun signUpUser(signUp: SignUp) = SignUpUser(UsersRepository(UserDataSource()))
        .invoke(createUserValidation()).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    signUp.showMessage(ID0_MESSAGE)
                    //TODO ("here we need to create the room repository with all the data of the user")
                    // the information por the user need to be like:
                    // UserName:
                    // Email:
                    // Avatar:
                    // Friends:
                    // List:
                    // BlockedUser:
                    // Setting:
                }
                Status.ERROR -> {
                    when (it.codeException){
                        "ERROR_INVALID_EMAIL" ->{ signUp.showMessage(ID1_MESSAGE) }
                        "ERROR_EMAIL_ALREADY_IN_USE" ->{ signUp.showMessage(ID2_MESSAGE) }
                    }}}}


    private fun createUserValidation() =
        UserValidation(emailValue.value.toString(), passwordValue.value.toString())

    private fun saveTokenNewUserLogin(){
        //TODO("this function need to save the user login of the new user in a sharePreferences")
        //TODO("will be call when the user do a sign up successful, for keep user login, with the new user created")
    }

    fun validateUsername(): Boolean {
        //TODO ("Implement this function")
        // what we need to check:
        // case1: If the string is correctly to be a username
        // case2: If the username is free to be used
        return true
    }
}

/*
("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
("ERROR_WEAK_PASSWORD", "The given password is invalid."));
("ERROR_MISSING_EMAIL", "An email address must be provided.";*/
