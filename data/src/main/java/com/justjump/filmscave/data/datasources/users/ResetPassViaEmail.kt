package com.justjump.filmscave.data.datasources.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justjump.filmscave.data._interfaces.ResetPasswordEmailDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseAuthDataSource
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResetPassViaEmail: ResetPasswordEmailDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()
    private val usersFirebaseAuth = UsersFirebaseAuthDataSource()

    override fun resetPassViaEmail(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {
            val result = usersFirebaseAuth.sendPasswordResetEmail(userValidationDataModel)

            if (result.status){ messageCreateUser.value = Resource.success() }
            else { messageCreateUser.value = Resource.error(result.codeException) }
        }
        return messageCreateUser
    }
}