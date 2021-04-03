package com.justjump.filmscave.data._interfaces

import android.content.Context
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserValidationDataModel

interface LogInIDataSource {

    fun logIn(
        appContext: Context,
        userValidationDataModel: UserValidationDataModel,
    ): LiveData<Resource<String>>
}