package com.justjump.filmscave.userArea.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.FriendDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    var numFriendsRequest = MutableLiveData<Int>()

    fun getFriends(appContext: Context): ArrayList<FriendDataModel> {
        return UsersLocalDataSource(RoomDataSource()).getUser(appContext)!!.friends
    }

    fun getNumFriendsRequest() {
        GlobalScope.launch(Dispatchers.Main){
            numFriendsRequest.value =  UsersFirebaseDataSource().getFriendsRequestNumbers("jjsotoramos@hotmail.com")
        }
    }
}