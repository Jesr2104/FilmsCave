package com.justjump.filmscave._viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource

class MainViewModel: ViewModel() {

    fun checkPreviewSession(context: Context) =
        UsersLocalDataSource(RoomDataSource()).activeSession(context)

    fun createAnonymousUser(appContext: Context, roomDataSource: RoomDataSource){
        val anonymousUser = UserStructureDataModel(
            userName = "Anonymous",
            email = "Anonymous@nn.com",
            avatar = "anonymous avatar"
        )
        roomDataSource.insertNewUser(appContext,anonymousUser)
    }
}