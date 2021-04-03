package com.justjump.filmscave.data.repositories.users

import android.content.Context
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._interfaces.LogInIDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserValidationDataModel

class LogInRepository(private val logInIDataSource: LogInIDataSource) {

    fun logInUser( appContext: Context,userValidationDataModel: UserValidationDataModel
        ): LiveData<Resource<String>> {

        return logInIDataSource.logIn(appContext, userValidationDataModel)
    }
}