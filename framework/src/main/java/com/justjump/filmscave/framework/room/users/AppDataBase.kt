package com.justjump.filmscave.framework.room.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserRoom::class],version = 1)
abstract class AppDataBase: RoomDatabase() {

    companion object {
        private const val NAME_DATABASE = "LocalUserDB"

        fun getDatabase(context: Context) =
            Room.databaseBuilder( context,
            AppDataBase::class.java,
            NAME_DATABASE ).allowMainThreadQueries().build()
    }
    abstract fun usersDao(): UsersDao
}