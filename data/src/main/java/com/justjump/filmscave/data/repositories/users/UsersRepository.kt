package com.justjump.filmscave.data.repositories.users

import Resource
import androidx.lifecycle.LiveData
import com.justjump.filmscave.domain.users.UserValidation

class UsersRepository(
    /*private val localDataSource: LocalUserDataSource,*/
    private val remoteUserDataSource: RemoteUserDataSource) {

    fun signUpUser(user: UserValidation): LiveData<Resource<String>> {
        // we call the function to create the user on the server Firebase
        // this function will be implement in data
        return remoteUserDataSource.signUp(user)
    }
}











// esto es como si no estuviese aqui por que no tiene nada que ver con lo de arriba
// arriba se recibe una instancia de esa interface pero que se reciben por parametro

interface LocalUserDataSource{

}

interface RemoteUserDataSource{
    fun signUp(user: UserValidation): LiveData<Resource<String>>
}