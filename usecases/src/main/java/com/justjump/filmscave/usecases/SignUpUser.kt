package com.justjump.filmscave.usecases.user_auth

class SignUpUser() {

    suspend fun invoke(
        name: String,
        user: String,
        password: String
    ): Boolean {

        return true
    }
}
