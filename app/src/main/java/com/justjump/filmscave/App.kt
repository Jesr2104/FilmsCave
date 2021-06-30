package com.justjump.filmscave

import android.app.Application
import com.justjump.filmscave.daggerComponent.MyComponent
import com.justjump.filmscave.daggerComponent.DaggerMyComponent
import com.justjump.filmscave.daggerModule.MyModule

class App: Application() {

    private lateinit var myComponent: MyComponent

    override fun onCreate() {
        super.onCreate()
        myComponent = DaggerMyComponent.builder().myModule(MyModule()).build()
    }

    fun getComponent() = myComponent
}