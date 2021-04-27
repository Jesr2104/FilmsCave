package com.justjump.filmscave.domain.users

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class UserStructureDataModel(
    val userName: String = "",
    var typeUser: String = "",
    val email: String = "",
    val avatar: String = "avatar/default_avatar.svg",
    val mobileNumber: String = "",
    val date: String = Calendar.getInstance().time.toString(),
    val friends: ArrayList<String> = java.util.ArrayList<String>(),
    val list: ArrayList<String> = java.util.ArrayList<String>(),
    val blockedUsers: ArrayList<String> = java.util.ArrayList<String>(),
    val setting: HashMap<String,Any> = HashMap()
    //TODO ("Change structure for complete definition")
)