package com.justjump.filmscave._viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data.datasources.users.local.UserDataSource
import com.justjump.filmscave.framework.room.users.RoomDataSource

class MainViewModel: ViewModel() {

    fun checkPreviewSession(context: Context) =
        UserDataSource(RoomDataSource()).activeSession(context)
}