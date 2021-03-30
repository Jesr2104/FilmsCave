package com.justjump.filmscave.domain.users

data class UserStructureDataModel(
    val userName: String,
    val email: String,
    val avatar: String,
    val friends: ArrayList<String>,
    val list: ArrayList<String>,
    val blockedUsers: ArrayList<String>,
    val setting: String
)