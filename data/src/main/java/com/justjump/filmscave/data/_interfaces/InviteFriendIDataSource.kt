package com.justjump.filmscave.data._interfaces

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.FriendDataModel

interface InviteFriendIDataSource {
    fun inviteFriend(username: String, thisUser: FriendDataModel): LiveData<Resource<String>>
}