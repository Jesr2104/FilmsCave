package com.justjump.filmscave.data.datasources.users

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data._interfaces.SignUpIDataSource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseAuthDataSource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUp(private val roomFrameworkDataSource: RoomFrameworkDataSource) : SignUpIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()
    private val usersFirebase = UsersFirebaseDataSource()
    private val usersFirebaseAuth = UsersFirebaseAuthDataSource()

    // this is the implementation of the signUp user on the server of data bases
    override fun signUp( appContext: Context, userValidationDataModel: UserValidationDataModel,
        userStructureDataModel: UserStructureDataModel ): LiveData<Resource<String>> {

        GlobalScope.launch(Dispatchers.Main) {
            // check if the user name is able to used
            if (usersFirebase.checkUserName(userStructureDataModel.userName)){
                /* Firebase Auth */
                val result = usersFirebaseAuth.signUpUser(userValidationDataModel)
                if (result.status){
                    if (/* Room Active Session => */ roomFrameworkDataSource.insertNewUser(appContext,userStructureDataModel) &&
                        /* Firebase Cloud FireStore => */ usersFirebase.insertUser(userStructureDataModel)) {
                        messageCreateUser.value = Resource.success()
                    }
                } else {
                    messageCreateUser.value = Resource.error(result.codeException)
                }
            } else {
                messageCreateUser.value = Resource.error("ERROR_USERNAME_NOT_ABLE")
            }
        }
        return messageCreateUser
    }

    override fun signUpGoogle(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun signUpFacebook(userValidationDataModel: UserValidationDataModel): LiveData<Resource<String>> {
        TODO("Not yet implemented")
    }
}