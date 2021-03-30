package com.justjump.filmscave.framework.room.users

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserRoom::class],version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun usersDao(): UsersDao
}