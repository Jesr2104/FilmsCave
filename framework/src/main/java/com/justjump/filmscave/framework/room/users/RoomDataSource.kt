package com.justjump.filmscave.framework.room.users

import android.content.Context
import com.justjump.filmscave.data._interfaces.RoomFramework
import com.justjump.filmscave.domain.users.UserStructureDataModel

class RoomDataSource: RoomFramework {
    override fun bindToRoom(Context: Context, UserStructureDataModel: UserStructureDataModel): Boolean {
        return true
    }
}