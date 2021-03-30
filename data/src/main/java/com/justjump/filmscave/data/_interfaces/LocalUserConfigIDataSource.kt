package com.justjump.filmscave.data._interfaces

import android.content.Context
import com.justjump.filmscave.domain.users.UserStructureDataModel

interface LocalUserConfigIDataSource {
    fun localUserConfig(appContext: Context, userStructureDataMode: UserStructureDataModel):Boolean
}