package com.justjump.filmscave.framework.room.users

import android.content.Context
import com.justjump.filmscave.data._interfaces.RoomFramework
import com.justjump.filmscave.domain.users.UserStructureDataModel
import java.util.ArrayList

class RoomDataSource : RoomFramework {

    override fun insertNewUser(
        context: Context,
        userStructureDataModel: UserStructureDataModel ): Boolean {

        val userActive = UserRoom(
            userStructureDataModel.email,
            userStructureDataModel.userName,
            userStructureDataModel.avatar,
//            userStructureDataModel.list,
//            userStructureDataModel.friends,
//            userStructureDataModel.blockedUsers,
            userStructureDataModel.setting
        )

        AppDataBase.getDatabase(context).usersDao().delete()
        AppDataBase.getDatabase(context).usersDao().insertUser(userActive)

        return true
    }

    override fun getUser(context: Context): UserStructureDataModel {

        val data = AppDataBase.getDatabase(context).usersDao().getUser()
        return UserStructureDataModel(
            data.userName,
            data.email,
            data.avatar,
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ""
        )
    }

}