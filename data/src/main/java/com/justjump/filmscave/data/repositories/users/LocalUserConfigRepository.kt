package com.justjump.filmscave.data.repositories.users

import android.content.Context
import com.justjump.filmscave.data._interfaces.LocalUserConfigIDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel

class LocalUserConfigRepository(private val localUserConfigIDataSource: LocalUserConfigIDataSource) {

    fun localDataUserConfig(context: Context, userStructureDataModel: UserStructureDataModel): Boolean{
        return localUserConfigIDataSource.localUserConfig(context, userStructureDataModel)
    }
}