package com.justjump.filmscave.framework.room.users

import androidx.room.*


@Dao
interface UsersDao {

    @Query("SELECT * FROM UserRoom")
    fun getUser(): UserRoom

    @Query("DELETE FROM UserRoom")
    fun delete()

    @Insert
    fun insertUser(user: UserRoom)

    @Update
    fun updateUser(user: UserRoom)
}