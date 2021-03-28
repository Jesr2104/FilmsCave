package com.justjump.filmscave.usecases

import com.justjump.filmscave.data.repositories.users.UsersRepository
import com.justjump.filmscave.domain.users.UserValidation

class SignUpUser(private val usersRepository: UsersRepository) {

    // this usecases will be in charge to create the new users
    fun invoke(user: UserValidation): Boolean = usersRepository.signUpUser(user)
}
