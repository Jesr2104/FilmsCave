package com.justjump.filmscave.usecases

class LoginUser(
    private val user: String,
    private val password: String) {

    suspend fun invoke(): Boolean{

        return true
    }
}