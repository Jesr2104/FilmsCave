package com.justjump.filmscave.data.datasources.users

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justjump.filmscave.data._interfaces.LogInIDataSource
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseAuthDataSource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogIn(private val roomFrameworkDataSource: RoomFrameworkDataSource): LogInIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()
    private val usersFirebase = UsersFirebaseDataSource()
    private val usersFirebaseAuth = UsersFirebaseAuthDataSource()

    override fun logIn( appContext: Context, userValidationDataModel: UserValidationDataModel
        ): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {
            val result = usersFirebaseAuth.logInUser(userValidationDataModel)
            if (result.status){
                if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(appContext,
                    /* Firebase Cloud FireStore => */ usersFirebase.getUser(userValidationDataModel.email))){
                    messageCreateUser.value = Resource.success()
                }
            } else {messageCreateUser.value = Resource.error(result.codeException)}
        }
        return messageCreateUser
    }
}