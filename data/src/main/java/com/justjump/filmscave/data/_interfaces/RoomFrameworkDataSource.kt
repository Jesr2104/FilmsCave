package com.justjump.filmscave.data._interfaces

import android.content.Context
import com.justjump.filmscave.domain.users.UserStructureDataModel

interface RoomFrameworkDataSource {
    fun insertNewUser(context: Context, userStructureDataModel: UserStructureDataModel): Boolean
    fun getUser(context: Context): UserStructureDataModel?
    fun checkSession(context: Context): Boolean
    fun closeSession(context: Context): Boolean
}