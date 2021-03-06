package com.justjump.filmscave.userArea.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.R
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.userArea.InviteFriend
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.data.repositories.userArea.InvitationFriendRepository
import com.justjump.filmscave.domain.users.FriendDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource
import com.justjump.filmscave.usecases.userArea.InviteFriendUseCases
import com.justjump.filmscave.userArea.InviteFriendFragment

class InviteFriendViewModel: ViewModel() {

    interface Message{ fun showMessage(message: Int, success: Boolean, fieldError: Int) }

    var userNameValue = MutableLiveData<String>()

    fun inviteFriend(inviteFriendFragment: InviteFriendFragment, thisUser: FriendDataModel) =
        InviteFriendUseCases(InvitationFriendRepository(InviteFriend())).invoke(userNameValue.value.toString(), thisUser).observeForever{
        when (it.status) {
            Status.SUCCESS -> {
                inviteFriendFragment.showMessage(R.string.cero,true, 0)
            }
            Status.ERROR -> {
                when (it.codeException){
                    "1" ->{ inviteFriendFragment.showMessage(R.string.uno, false,2) }
                    "100" ->{ inviteFriendFragment.showMessage(R.string.cien, false, 2) }
                }}}
        }

    fun getMyUsername(appContext: Context): FriendDataModel {
        val user = UsersLocalDataSource(RoomDataSource()).getUser(appContext)
        return FriendDataModel(
            Username = user!!.userName,
            Email = user.email
        )
    }

}