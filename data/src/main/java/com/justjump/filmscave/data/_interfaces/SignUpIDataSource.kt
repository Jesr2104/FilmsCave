package com.justjump.filmscave.data._interfaces

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserValidationDataModel

interface SignUpIDataSource {
    fun signUp(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>>
    fun signUpGoogle(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>>
    fun signUpFacebook(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>>
}