package com.justjump.filmscave.users.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.R
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.users.LogIn
import com.justjump.filmscave.data.repositories.users.LogInRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource
import com.justjump.filmscave.usecases.users.LogInUseCases
import com.justjump.filmscave.users.LoginFragment

class LogInViewModel: ViewModel() {

    interface Message{
        fun showMessage(message: Int, success: Boolean)
    }

    var emailValue = MutableLiveData<String>()
    var passwordValue = MutableLiveData<String>()

    fun logInUser(logInFragment: LoginFragment, appContext: Context) = LogInUseCases(LogInRepository(
        LogIn(RoomDataSource())
    ))
        .invoke(appContext, createUserValidation()).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    logInFragment.showMessage(R.string.id_message_login_successfully, true)
                }
                Status.ERROR -> {
                    when (it.codeException){
                    "ERROR_WRONG_PASSWORD" -> { logInFragment.showMessage(R.string.id_message_password_invalid, false)}
                    "ERROR_INVALID_EMAIL" -> { logInFragment.showMessage(R.string.id_message_email_invalid, false)}
                    "ERROR_USER_NOT_FOUND" -> { logInFragment.showMessage(R.string.id_message_email_not_registered, false)}
                }}}}

    private fun createUserValidation() =
        UserValidationDataModel(emailValue.value.toString(), passwordValue.value.toString())
}