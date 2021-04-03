package com.justjump.filmscave.framework.room.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersDao {

    @Query("SELECT * FROM UserRoom")
    fun getUser(): UserRoom

    // this function clear the table User Room
    @Query(value = "DELETE FROM UserRoom")
    fun delete()

    @Insert
    fun insertUser(user: UserRoom)

    @Update
    fun updateUser(user: UserRoom)
}