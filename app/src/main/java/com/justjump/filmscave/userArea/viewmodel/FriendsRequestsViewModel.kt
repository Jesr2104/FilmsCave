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

class FriendsRequestsViewModel : ViewModel() {

    var friendsRequestList = MutableLiveData<ArrayList<FriendDataModel>>()
    var checkForRemove = MutableLiveData<Boolean>()
    var checkForConfirm = MutableLiveData<Boolean>()

    fun getFriendsRequest(appContext: Context) {
        GlobalScope.launch(Dispatchers.Main){
            friendsRequestList.value =  UsersFirebaseDataSource().getFriendsRequest(
                UsersLocalDataSource(RoomDataSource()).getUser(appContext)!!.email)
        }
    }

    fun removeFriendRequest(appContext: Context, email: String) {
        GlobalScope.launch(Dispatchers.Main){
            // first parameter mail of the user
            // second parameter mail of the friend request
            checkForRemove.value = UsersFirebaseDataSource().removeFriendRequest(
                UsersLocalDataSource(RoomDataSource()).getUser(appContext)!!.email,
                email)
        }
    }

    fun confirmFriendRequest(appContext: Context, email: String) {
        GlobalScope.launch(Dispatchers.Main){
            // first parameter mail of the user
            // second parameter mail of the friend request
            checkForConfirm.value = UsersFirebaseDataSource().confirmFriendRequest(
                UsersLocalDataSource(RoomDataSource()).getUser(appContext)!!.email,
                UsersLocalDataSource(RoomDataSource()).getUser(appContext)!!.userName,
                email)
        }
    }
}