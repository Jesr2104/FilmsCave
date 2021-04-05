package com.justjump.filmscave.data.datasources.users.remote

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.justjump.filmscave.domain.users.UserStructureDataModel
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UsersFirebaseDataSource {

    // instance of the firebase to implement all the solutions
    private var databaseInstance: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun insertUser(userStructureDataModel: UserStructureDataModel): Boolean{
        var result = false
        return try {
            databaseInstance.collection("user").document(userStructureDataModel.email).set(
                hashMapOf(
                    "username" to userStructureDataModel.userName,
                    "avatar" to userStructureDataModel.avatar,
                    "setting" to userStructureDataModel.setting,
                )
            ).addOnSuccessListener { result = true }.await()
            result

        } catch (e: Exception){
            Log.e("Tag: Jesr2104", e.message.toString())
            result
        }
    }

    suspend fun getUser(email: String): UserStructureDataModel {
        return try {
            val data = databaseInstance.collection("users").document(email).get().await()
            UserStructureDataModel(
                userName = data.get("username") as String,
                email = email,
                avatar = data.get("avatar") as String,
            )
        } catch (e: FirebaseException){
            UserStructureDataModel()
        }
    }

    suspend fun checkUserName(){

    }

    fun removeUser(){
    }

    fun editUser(){
    }
}