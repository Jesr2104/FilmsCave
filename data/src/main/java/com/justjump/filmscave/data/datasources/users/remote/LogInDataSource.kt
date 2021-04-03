package com.justjump.filmscave.data.datasources.users.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.justjump.filmscave.data._interfaces.LogInIDataSource
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogInDataSource(private val roomFrameworkDataSource: RoomFrameworkDataSource): LogInIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()
    private val usersFirebase = UsersFirebaseDataSource()

    override fun logIn( appContext: Context, userValidationDataModel: UserValidationDataModel
        ): LiveData<Resource<String>> {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            userValidationDataModel.email.trim(),
            userValidationDataModel.password.trim()
        )
            .addOnCompleteListener { task ->
                task.addOnFailureListener {
                    val errorCode = (task.exception as FirebaseAuthException?)!!.errorCode
                    messageCreateUser.value = Resource.error(errorCode)
                }
                task.addOnSuccessListener {

                    GlobalScope.launch(Dispatchers.Main) {
                        val data = usersFirebase.getUser(userValidationDataModel.email)
                        roomFrameworkDataSource.insertNewUser(appContext,data)
                        messageCreateUser.value = Resource.success()
                    }
                }
            }
        return messageCreateUser
    }
}