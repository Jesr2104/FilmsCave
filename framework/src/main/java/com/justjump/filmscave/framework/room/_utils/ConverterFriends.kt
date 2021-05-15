package com.justjump.filmscave.framework.room._utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.justjump.filmscave.domain.users.FriendDataModel

class ConverterFriends {

    @TypeConverter
    fun stringToMap(friends: String): ArrayList<FriendDataModel> {
        return Gson().fromJson(friends,  object : TypeToken<ArrayList<FriendDataModel>>() {}.type)
    }

    @TypeConverter
    fun mapToString(friends: ArrayList<FriendDataModel>?): String {
        return if(friends == null) "" else Gson().toJson(friends)
    }
}