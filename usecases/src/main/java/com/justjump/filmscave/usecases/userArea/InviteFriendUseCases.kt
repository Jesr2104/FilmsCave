package com.justjump.filmscave.usecases.userArea

import androidx.lifecycle.LiveData
import com.justjump.filmscave.data._utils.Resource
import com.justjump.filmscave.data.repositories.userArea.InvitationFriendRepository
import com.justjump.filmscave.domain.users.FriendDataModel

class InviteFriendUseCases(private val invitationFriendRepository: InvitationFriendRepository) {

    fun invoke(username: String, thisUser: FriendDataModel): LiveData<Resource<String>> =
        invitationFriendRepository.inviteFriend(username, thisUser)
}