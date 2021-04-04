package com.justjump.filmscave._viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.framework.room.users.RoomDataSource

class MainViewModel: ViewModel() {

    fun checkPreviewSession(context: Context) =
        UsersLocalDataSource(RoomDataSource()).activeSession(context)
}