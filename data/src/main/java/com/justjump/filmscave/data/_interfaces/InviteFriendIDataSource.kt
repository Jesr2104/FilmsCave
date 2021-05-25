package com.justjump.filmscave.data._interfaces

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource

interface InviteFriendIDataSource {
    fun inviteFriend(username: String): LiveData<Resource<String>>
}