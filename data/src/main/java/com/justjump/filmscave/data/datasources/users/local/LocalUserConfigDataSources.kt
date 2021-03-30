package com.justjump.filmscave.data.datasources.users.local

import android.content.Context
import com.justjump.filmscave.data._interfaces.LocalUserConfigIDataSource
import com.justjump.filmscave.data._interfaces.RoomFramework
import com.justjump.filmscave.domain.users.UserStructureDataModel

class LocalUserConfigDataSources(private val roomFramework: RoomFramework): LocalUserConfigIDataSource {

    override fun localUserConfig(appContext: Context, userStructureDataModel: UserStructureDataModel ): Boolean {
        roomFramework.bindToRoom(appContext,userStructureDataModel)

        return true
    }
}