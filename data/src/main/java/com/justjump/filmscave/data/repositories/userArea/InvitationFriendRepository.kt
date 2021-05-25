package com.justjump.filmscave.data.repositories.userArea

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._interfaces.InviteFriendIDataSource
import com.justjump.filmscave.data._utils.Resource

class InvitationFriendRepository(private val inviteFriendIDataSource: InviteFriendIDataSource) {

    fun inviteFriend(username: String): LiveData<Resource<String>> {
        return inviteFriendIDataSource.inviteFriend(username)
    }
}