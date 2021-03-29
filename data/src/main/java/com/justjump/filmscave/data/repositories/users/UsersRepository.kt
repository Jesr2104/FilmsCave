package com.justjump.filmscave.data.repositories.users

import com.justjump.filmscave.data.utils.Resource
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data.interfaces.RemoteUserDataSource
import com.justjump.filmscave.domain.users.UserValidation

class UsersRepository(private val remoteUserDataSource: RemoteUserDataSource) {

    fun signUpUser(user: UserValidation): LiveData<Resource<String>> {
        // we call the function to create the user on the server Firebase
        // this function will be implement in data
        return remoteUserDataSource.signUp(user)
    }
}