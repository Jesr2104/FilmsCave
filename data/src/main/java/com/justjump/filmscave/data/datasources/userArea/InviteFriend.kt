package com.justjump.filmscave.data.datasources.userArea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justjump.filmscave.data._interfaces.InviteFriendIDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.datasources.users.remote.UsersFirebaseDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InviteFriend: InviteFriendIDataSource {

    private var messageCreateUser = MutableLiveData<Resource<String>>()

    override fun inviteFriend(username: String): LiveData<Resource<String>> {
        GlobalScope.launch(Dispatchers.Main) {

            val new = UserStructureDataModel(email = "jjsotoramos@hotmail.com", userName = "Jesr2104")

            when {
                UsersFirebaseDataSource().inviteFriend(username, new) == 0 -> {
                    messageCreateUser.value = Resource.success()
                }
                UsersFirebaseDataSource().inviteFriend(username, new) == 1 -> {
                    messageCreateUser.value = Resource.error("1")
                }
                UsersFirebaseDataSource().inviteFriend(username, new) == 100 -> {
                    messageCreateUser.value = Resource.error("100")
                }
            }
        }
        return messageCreateUser
    }
}