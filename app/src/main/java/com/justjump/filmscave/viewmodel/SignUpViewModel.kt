package com.justjump.filmscave.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data.remote.UserDataSource
import com.justjump.filmscave.data.repositories.users.UsersRepository
import com.justjump.filmscave.domain.users.UserValidation
import com.justjump.filmscave.usecases.SignUpUser

class SignUpViewModel() : ViewModel() {

    var userValue = MutableLiveData<String>()
    var passwordValue = MutableLiveData<String>()

    fun signUpUser() =
        SignUpUser(UsersRepository(UserDataSource()))
            .invoke(createUserValidation())

    private fun createUserValidation() =
        UserValidation(userValue.toString(), passwordValue.toString())

}