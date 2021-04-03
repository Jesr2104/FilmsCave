package com.justjump.filmscave.data.datasources.users.local

import android.content.Context
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel

class UserDataSource(private val roomFrameworkDataSource: RoomFrameworkDataSource)  {

    fun getUser(context: Context): UserStructureDataModel? = roomFrameworkDataSource.getUser(context)

    fun activeSession(context: Context) = roomFrameworkDataSource.checkSession(context)

    fun signOut(context: Context) = roomFrameworkDataSource.closeSession(context)
}