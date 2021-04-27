package com.justjump.filmscave.users.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.justjump.filmscave.R
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.users.LogIn
import com.justjump.filmscave.data.repositories.users.LogInRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource
import com.justjump.filmscave.usecases.users.LogInUseCases
import com.justjump.filmscave.users.LoginFragment

class LogInViewModel: ViewModel() {

    interface Message{ fun showMessage(message: Int, success: Boolean, fieldError: Int) }

    var emailValue = MutableLiveData<String>()
    var passwordValue = MutableLiveData<String>()

    fun logInUser(logInFragment: LoginFragment, appContext: Context) = LogInUseCases(LogInRepository(
        LogIn(RoomDataSource())
    ))
        .invoke(appContext, createUserValidation()).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    logInFragment.showMessage(R.string.id_message_login_successfully, true,0)
                }
                Status.ERROR -> {
                    when (it.codeException){
                    "ERROR_WRONG_PASSWORD" -> { logInFragment.showMessage(R.string.id_message_password_invalid, false,2)}
                    "ERROR_INVALID_EMAIL" -> { logInFragment.showMessage(R.string.id_message_email_invalid, false,1)}
                    "ERROR_USER_NOT_FOUND" -> { logInFragment.showMessage(R.string.id_message_email_not_registered, false,0)}
                }}}}

    fun logInUserGoogle(loginFragment: LoginFragment, appContext: Context, account: GoogleSignInAccount) {
        LogIn(RoomDataSource()).logInGoogle(appContext, account).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    loginFragment.showMessage(R.string.id_message_sign_up_successful,true, 0)
                }
                Status.ERROR -> {
                    // No error has been identified
                }
            }
        }
    }

    private fun createUserValidation() = UserValidationDataModel(emailValue.value.toString(), passwordValue.value.toString())
}