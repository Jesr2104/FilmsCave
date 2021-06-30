package com.justjump.filmscave.daggerComponent

import com.justjump.filmscave.daggerModule.MyModule
import com.justjump.filmscave.users.viewmodel.LogInViewModel
import dagger.Component

@Component(modules = [MyModule::class])
interface MyComponent {
    fun inject(login: LogInViewModel)
}