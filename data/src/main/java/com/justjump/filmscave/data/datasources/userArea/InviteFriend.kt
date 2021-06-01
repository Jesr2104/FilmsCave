package com.justjump.filmscave.data.datasources.userArea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justjump.filmscave.data._interfaces.InviteFriendIDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.FriendDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InviteFriend: InviteFriendIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()

    override fun inviteFriend(username: String, thisUser: FriendDataModel): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {

            when {
                UsersFirebaseDataSource().inviteFriend(username, thisUser) == 0 -> {
                    messageCreateUser.value = Resource.success()
                }
                UsersFirebaseDataSource().inviteFriend(username, thisUser) == 1 -> {
                    messageCreateUser.value = Resource.error("1")
                }
                UsersFirebaseDataSource().inviteFriend(username, thisUser) == 100 -> {
                    messageCreateUser.value = Resource.error("100")
                }
            }
        }
        return messageCreateUser
    }
}