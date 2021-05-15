package com.justjump.filmscave.framework.room.users

import android.content.Context
import com.justjump.filmscave.data._interfaces.RoomFrameworkDataSource
import com.justjump.filmscave.domain.users.UserStructureDataModel

class RoomDataSource : RoomFrameworkDataSource {

    // function to do the stance of the database room
    private fun getInstanceDatabase(context: Context) = AppDataBase.getDatabase(context)

    // function to do the session for the new user
    override fun insertNewUser( context: Context, userStructureDataModel: UserStructureDataModel ): Boolean {
        val db = getInstanceDatabase(context)
        //TODO ("Change structure for room")
        val userActive = UserRoom(
            email = userStructureDataModel.email,
            avatar = userStructureDataModel.avatar,
            userName = userStructureDataModel.userName,
            date = userStructureDataModel.date,
            typeUser = userStructureDataModel.typeUser,
            setting = userStructureDataModel.setting,
            friends = userStructureDataModel.friends
        )

        // clear any another session active
        db.usersDao().delete()
        // save the session for the user
        db.usersDao().insertUser(userActive)
        // close the database
        db.close()
        return true
    }

    // function to return the information of the user.
    override fun getUser(context: Context): UserStructureDataModel? {
        val db = getInstanceDatabase(context)
        val user = db.usersDao().getUser()

        //TODO ("Change structure for room")
        if (user != null){
            return UserStructureDataModel(
                email = user.email,
                avatar = user.avatar,
                userName = user.userName,
                date = user.date,
                typeUser = user.typeUser,
                setting = user.setting,
                friends = user.friends
            )
        }
        db.close()
        return null
    }

    // function if is any user session active.
    override fun checkSession(context: Context): Boolean {
        val db = getInstanceDatabase(context)
        val user = db.usersDao().getUser()

        if (user != null){
            return true
        }
        db.close()

        return false
    }

    // close the active session
    override fun closeSession(context: Context): Boolean {
        val db = getInstanceDatabase(context)
        val dbDAO = db.usersDao()

        dbDAO.delete()
        db.close()
        return true
    }
}