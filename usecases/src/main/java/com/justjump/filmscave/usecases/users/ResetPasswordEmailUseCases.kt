package com.justjump.filmscave.usecases.users

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.repositories.users.ResetPasswordEmailRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel

class ResetPasswordEmailUseCases(private val resetPasswordEmailRepository: ResetPasswordEmailRepository) {

    // this function will be reset the password of the user of the user via Email
    fun invoke(
        userValidationDataModel: UserValidationDataModel
    ): LiveData<Resource<String>> = resetPasswordEmailRepository.resetPassViaEmail(userValidationDataModel)
}