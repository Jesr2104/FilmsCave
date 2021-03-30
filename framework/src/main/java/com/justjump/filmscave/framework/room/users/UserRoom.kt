package com.justjump.filmscave.framework.room.users

import androidx.room.*

@Entity
data class UserRoom (
    @PrimaryKey
    val email: String,
    val userName: String,
    val avatar: String,
    val friends: ArrayList<String>,
    val blockedUsers: ArrayList<String>,
    val setting: ArrayList<String>
)