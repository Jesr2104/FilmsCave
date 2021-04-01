package com.justjump.filmscave.usecases.users

import android.content.Context
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.repositories.users.SignUpRepository
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel

class SignUpUseCases(private val signUpRepository: SignUpRepository) {

    // this usecases will be in charge to create the new users with mail and password
    fun invoke(
        appContext: Context,
        userValidationDataModel: UserValidationDataModel,
        userStructureDataModel: UserStructureDataModel ): LiveData<Resource<String>> =
        signUpRepository.signUpUser(appContext, userValidationDataModel, userStructureDataModel)
}
