package com.justjump.filmscave.framework.room.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserRoom (
    @PrimaryKey
    val email: String,
    val userName: String,
    val avatar: String,
    //TODO ("Change structure for room")
//    val customsList: String,
//    val friends: String,
//    val blockedUsers: String,
//    val setting: String

)