package com.justjump.filmscave.data.datasources.users.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.justjump.filmscave.domain.users.UserStructureDataModel
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.ArrayList

class UsersFirebaseDataSource {

    var databaseInstance: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun insertUser(userStructureDataModel: UserStructureDataModel): Boolean{
        databaseInstance
            .collection("users")
            .document(userStructureDataModel.email)
            .set(

            hashMapOf(
                "username" to userStructureDataModel.userName,
                "avatar" to userStructureDataModel.avatar,
                "setting" to userStructureDataModel.setting,
            )
        )
        return true
    }

    suspend fun getUser(email: String): UserStructureDataModel {
        return try {
            val data = databaseInstance.collection("users").document(email).get().await()
            UserStructureDataModel(
                userName = data.get("username") as String,
                email = email,
                avatar = data.get("avatar") as String,
            )
        } catch (e: Exception){
            UserStructureDataModel()
        }
    }

    fun removeUser(){
    }

    fun editUser(){

    }
}