package com.justjump.filmscave.data.repositories.users

import android.content.Context
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data._interfaces.SignUpIDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel

class SignUpRepository(private val signUpIDataSource: SignUpIDataSource) {

    fun signUpUser( appContext: Context, userValidationDataModel: UserValidationDataModel,
        userStructureDataModel: UserStructureDataModel ): LiveData<Resource<String>> {

        return signUpIDataSource.signUp(appContext, userValidationDataModel, userStructureDataModel)
    }
}