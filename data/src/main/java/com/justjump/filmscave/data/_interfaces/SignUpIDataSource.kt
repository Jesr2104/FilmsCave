package com.justjump.filmscave.data._interfaces

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel

interface SignUpIDataSource {

    fun signUp(
        appContext: Context,
        userValidationDataModel: UserValidationDataModel,
        userStructureDataModel: UserStructureDataModel
    ): LiveData<Resource<String>>

    fun signUpGoogle(
        appContext: Context,
        account: GoogleSignInAccount,
        userStructureDataModel: UserStructureDataModel
    ): LiveData<Resource<String>>

    fun signUpFacebook(
        userValidationDataModel: UserValidationDataModel
    ): LiveData<Resource<String>>
}