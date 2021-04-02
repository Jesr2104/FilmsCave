package com.justjump.filmscave.framework.room.users

import android.content.Context
import com.justjump.filmscave.data._interfaces.RoomFramework
import com.justjump.filmscave.domain.users.UserStructureDataModel
import java.util.ArrayList

class RoomDataSource : RoomFramework {

    // function to do the stance of the database room
    private fun getInstanceDatabase(context: Context) = AppDataBase.getDatabase(context).usersDao()

    // function to do the session for the new user
    override fun insertNewUser( context: Context, userStructureDataModel: UserStructureDataModel ): Boolean {

        val db = getInstanceDatabase(context)

        //TODO ("is missing to change for the new structure of the data")
        val userActive = UserRoom(
            userStructureDataModel.email,
            userStructureDataModel.userName,
            userStructureDataModel.avatar,
            userStructureDataModel.setting
        )

        // clear any another session active
        db.delete()

        // save the session for the user
        db.insertUser(userActive)

        return true
    }

    // function to return the information of the user.
    override fun getUser(context: Context): UserStructureDataModel? {

        val user = getInstanceDatabase(context).getUser()

        if (user != null){
            return UserStructureDataModel(
                user.userName,
                user.email,
                user.avatar,
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ""
            )
        }
        return null
    }

    // function if is any user session active.
    override fun checkSession(context: Context): Boolean {
        val data = AppDataBase.getDatabase(context).usersDao().getUser()
        if (data != null){
            return true
        }
        return false
    }


}