package com.justjump.filmscave.data.repositories.users

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._interfaces.ResetPasswordEmailDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserValidationDataModel

class ResetPasswordEmailRepository(private val resetPasswordEmailDataSource: ResetPasswordEmailDataSource) {

    fun resetPassViaEmail(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {
        return resetPasswordEmailDataSource.resetPassViaEmail(userValidationDataModel)
    }
}