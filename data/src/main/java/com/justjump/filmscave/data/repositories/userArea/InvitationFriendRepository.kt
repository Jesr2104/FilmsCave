package com.justjump.filmscave.data.repositories.userArea

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._interfaces.InviteFriendIDataSource
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.domain.users.FriendDataModel

class InvitationFriendRepository(private val inviteFriendIDataSource: InviteFriendIDataSource) {

    fun inviteFriend(username: String, thisUser: FriendDataModel): LiveData<Resource<String>> {
        return inviteFriendIDataSource.inviteFriend(username, thisUser)
    }
}