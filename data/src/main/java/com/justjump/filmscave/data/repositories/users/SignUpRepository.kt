package com.justjump.filmscave.data.repositories.users

import android.content.Context
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data._interfaces.SignUpIDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel

class SignUpRepository(private val SignUpIDataSource: SignUpIDataSource) {

    fun signUpUser( appContext: Context, userValidationDataModel: UserValidationDataModel,
        userStructureDataModel: UserStructureDataModel ): LiveData<Resource<String>> {

        // we call the function to create the user on the server Firebase
        // this function will be implement in data
        return SignUpIDataSource.signUp(appContext, userValidationDataModel, userStructureDataModel)
    }
}