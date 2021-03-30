package com.justjump.filmscave.data._interfaces

import android.content.Context
import com.justjump.filmscave.domain.users.UserStructureDataModel

interface RoomFramework {
    fun bindToRoom(Context: Context, UserStructureDataModel: UserStructureDataModel): Boolean
}