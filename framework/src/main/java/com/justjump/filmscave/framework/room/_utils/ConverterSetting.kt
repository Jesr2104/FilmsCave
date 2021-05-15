package com.justjump.filmscave.framework.room._utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterSetting {

    @TypeConverter
    fun stringToMap(Setting: String): HashMap<String, String> {
        return Gson().fromJson(Setting,  object : TypeToken<HashMap<String, String>>() {}.type)
    }

    @TypeConverter
    fun mapToString(Setting: HashMap<String, String>?): String {
        return if(Setting == null) "" else Gson().toJson(Setting)
    }
}