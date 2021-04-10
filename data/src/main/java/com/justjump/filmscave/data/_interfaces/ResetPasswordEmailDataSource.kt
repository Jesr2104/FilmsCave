package com.justjump.filmscave.data._interfaces

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserValidationDataModel

interface ResetPasswordEmailDataSource {
    fun resetPassViaEmail(
        userValidationDataModel: UserValidationDataModel
    ): LiveData<Resource<String>>
}