package com.justjump.filmscave.daggerModule

import com.justjump.filmscave.framework.room.users.RoomDataSource
import dagger.Module
import dagger.Provides

@Module
class MyModule {

    @Provides
    fun provideRoomUser() = RoomDataSource()
}