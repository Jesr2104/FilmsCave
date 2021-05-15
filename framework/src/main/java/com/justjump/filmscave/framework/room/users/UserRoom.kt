package com.justjump.filmscave.framework.room.users

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.justjump.filmscave.domain.users.FriendDataModel
import java.lang.reflect.Array

@Entity
data class UserRoom(
    @PrimaryKey
    val email: String,
    val avatar: String,
    val userName: String,
    val date: String,
    val typeUser: String,
    val setting: HashMap<String, String>,
    val friends: ArrayList<FriendDataModel>,
//    val customsList: String,
//    val blockedUsers: String,
)