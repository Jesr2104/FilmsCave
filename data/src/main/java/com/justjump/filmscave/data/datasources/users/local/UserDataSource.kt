package com.justjump.filmscave.data.datasources.users.local

import android.content.Context
import com.justjump.filmscave.data._interfaces.RoomFramework
import com.justjump.filmscave.domain.users.UserStructureDataModel

class UserDataSource(private val roomFramework: RoomFramework)  {

    fun getUser(context: Context): UserStructureDataModel? = roomFramework.getUser(context)

    fun activeSession(context: Context) = roomFramework.checkSession(context)
}