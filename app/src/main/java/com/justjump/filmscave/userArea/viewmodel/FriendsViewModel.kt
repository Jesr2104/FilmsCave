package com.justjump.filmscave.userArea.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.domain.users.FriendDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource

class FriendsViewModel: ViewModel() {

    fun getFriends(appContext: Context): ArrayList<FriendDataModel> {
        return UsersLocalDataSource(RoomDataSource()).getUser(appContext)!!.friends
    }

    fun getFriendsRequest(): Boolean{

        // check for the friends request
        return true
    }
}