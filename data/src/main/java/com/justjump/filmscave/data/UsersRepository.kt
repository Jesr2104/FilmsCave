package com.justjump.filmscave.data.user_auth

class UsersRepository(
    private val localDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource
) {


}

interface LocalUserDataSource{

}

interface RemoteUserDataSource{
 fun doLogin(user: String, password: String)
}