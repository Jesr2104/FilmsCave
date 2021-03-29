package com.justjump.filmscave.data.interfaces

import com.justjump.filmscave.data.utils.Resource
import androidx.lifecycle.LiveData
import com.justjump.filmscave.domain.users.UserValidation

interface RemoteUserDataSource {
    fun signUp(user: UserValidation): LiveData<Resource<String>>
    /*fun signUpGoogle(user: UserValidation): LiveData<Resource<String>>*/
}