package com.justjump.filmscave.users.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.users.remote.LogInDataSource
import com.justjump.filmscave.data.repositories.users.LogInRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource
import com.justjump.filmscave.usecases.users.LogInUseCases
import com.justjump.filmscave.users.LoginFragment

class LogInViewModel: ViewModel() {

    companion object{
        var ID0_MESSAGE = "The user has been successfully logged in."
        var ID1_MESSAGE = "The password is invalid."
        var ID2_MESSAGE = "There is no user with this email account."
    }

    interface Message{
        fun showMessage(message: String, success: Boolean)
    }

    var emailValue = MutableLiveData<String>()
    var passwordValue = MutableLiveData<String>()

    fun logInUser(logInFragment: LoginFragment, appContext: Context) = LogInUseCases(LogInRepository(LogInDataSource(RoomDataSource())))
        .invoke(appContext, createUserValidation()).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    logInFragment.showMessage(ID0_MESSAGE, true)
                }
                Status.ERROR -> {
                    when (it.codeException){
                    "ERROR_WRONG_PASSWORD" -> { logInFragment.showMessage(ID1_MESSAGE, false)}
                    "ERROR_USER_NOT_FOUND" -> { logInFragment.showMessage(ID2_MESSAGE, false)}
                }}}}

    private fun createUserValidation() =
        UserValidationDataModel(emailValue.value.toString(), passwordValue.value.toString())
}