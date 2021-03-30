package com.justjump.filmscave.framework.room.users

import androidx.room.*

@Dao
interface UsersDao {

    @Query("SELECT * FROM UserRoom")
    fun getUser(): UserRoom

    @Insert
    fun insertUser(user: Unit): Boolean

    @Delete
    fun deleteUser(): Boolean

    @Update
    fun updateUser(user: Unit): Boolean
}