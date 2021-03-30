package com.justjump.filmscave.usecases.users

import android.content.Context
import com.justjump.filmscave.data.repositories.users.LocalUserConfigRepository
import com.justjump.filmscave.domain.users.UserStructureDataModel

class LocalUserConfigUseCases(private val localUserConfigRepository: LocalUserConfigRepository) {

    // this usecases will be in charge to create the structure of the user on the database
    fun invoke(appContext: Context, userStructureDataModel: UserStructureDataModel):Boolean
        = localUserConfigRepository.localDataUserConfig(appContext, userStructureDataModel)
}