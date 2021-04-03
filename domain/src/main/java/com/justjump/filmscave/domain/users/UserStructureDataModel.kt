package com.justjump.filmscave.domain.users

data class UserStructureDataModel(
    val userName: String = "",
    val email: String = "",
    val avatar: String = "",
    val friends: ArrayList<String> = java.util.ArrayList<String>(),
    val list: ArrayList<String> = java.util.ArrayList<String>(),
    val blockedUsers: ArrayList<String> = java.util.ArrayList<String>(),
    val setting: String = ""
    //TODO ("Change structure for complete definition")
)