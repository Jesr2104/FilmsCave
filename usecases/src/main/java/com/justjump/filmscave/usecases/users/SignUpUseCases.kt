package com.justjump.filmscave.usecases.users

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.repositories.users.SignUpRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel

class SignUpUseCases(private val signUpRepository: SignUpRepository) {

    // this usecases will be in charge to create the new users with mail and password
    fun invoke(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> =
        signUpRepository.signUpUser(userValidationDataModel)
}
