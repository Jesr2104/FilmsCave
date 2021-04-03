package com.justjump.filmscave.usecases.users

import android.content.Context
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.repositories.users.LogInRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel

class LogInUseCases(private val logInRepository: LogInRepository) {

    // this usecases will be in charge to logIn users with mail and password
    fun invoke(
        appContext: Context,
        userValidationDataModel: UserValidationDataModel
    ): LiveData<Resource<String>> = logInRepository.logInUser(appContext, userValidationDataModel)
}