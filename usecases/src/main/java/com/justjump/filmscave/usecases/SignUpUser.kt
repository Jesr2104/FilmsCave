package com.justjump.filmscave.usecases

import com.justjump.filmscave.data.utils.Resource
import androidx.lifecycle.LiveData
import com.justjump.filmscave.data.repositories.users.UsersRepository
import com.justjump.filmscave.domain.users.UserValidation

class SignUpUser(private val usersRepository: UsersRepository) {

    // this usecases will be in charge to create the new users with mail and password
    fun invoke(user: UserValidation): LiveData<Resource<String>> = usersRepository.signUpUser(user)
}
