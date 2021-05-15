package com.justjump.filmscave.userArea.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.login.LoginManager
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.framework.room.users.RoomDataSource

class UserAreaViewModel : ViewModel() {

    var userNameValue = MutableLiveData<String>()
    var emailValue = MutableLiveData<String>()

    fun closeSession(appContext: Context) {
        val data = UsersLocalDataSource(RoomDataSource()).getUser(appContext)
        UsersLocalDataSource(RoomDataSource()).signOut(appContext)

        // logout facebook
        if (data != null) {
            if (data.typeUser == "Facebook") {
                LoginManager.getInstance().logOut()
            }
        }
    }

    fun loadUserData(appContext: Context) {
        val data = UsersLocalDataSource(RoomDataSource()).getUser(appContext)

        if (data != null) {
            emailValue.value = data.email
            userNameValue.value = data.userName
        }
    }
}