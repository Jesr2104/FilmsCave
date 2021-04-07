package com.justjump.filmscave.data.datasources.users.remote

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.justjump.filmscave.domain.users.UserStructureDataModel
import kotlinx.coroutines.tasks.await

class UsersFirebaseDataSource {

    companion object{
        const val COLLECTION_USERS = "users"
        const val COLLECTION_USERNAME = "usernames"
    }

    // instance of the firebase to implement all the solutions
    private var databaseInstance: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun insertUser(userStructureDataModel: UserStructureDataModel): Boolean{
        var result = false
        val userId = userStructureDataModel.email.trim()

        val setting = hashMapOf(
            "language" to "default"
        )

        // user information
        val user = hashMapOf(
            "username" to createUsername(userStructureDataModel.userName),
            "email" to userStructureDataModel.email,
            "avatar" to userStructureDataModel.avatar,
            "date" to userStructureDataModel.date,
            "setting" to setting,
        )

        return try {
            databaseInstance
                .collection(COLLECTION_USERS)
                .document(userId)
                .set(user).addOnSuccessListener { result = true }.await()

            // create the collections for Friends, Custom, BlockedUsers
            createFriendsList(userStructureDataModel.email)
            createCustomList(userStructureDataModel.email)
            createBlockedUsers(userStructureDataModel.email)

            result

        } catch (e: Exception){
            Log.e("Jesr2104 => ", e.message.toString())
            result
        }
    }

    suspend fun getUser(email: String): UserStructureDataModel {
        return try {
            val userDataFireStore = databaseInstance.collection(COLLECTION_USERS).document(email.trim()).get().await()

            UserStructureDataModel(
                userName = "Jesr2104",
                email = userDataFireStore.get("email") as String,
                avatar = userDataFireStore.get("avatar") as String,
                date = userDataFireStore.get("date") as String,
                setting = userDataFireStore.get("setting") as HashMap<String,Any>
            )
        } catch (e: FirebaseException){
            UserStructureDataModel()
        }
    }

    private suspend fun createUsername(username: String) =
        databaseInstance.collection(COLLECTION_USERNAME).add(hashMapOf("username" to username)).await()

    private suspend fun createFriendsList(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection("friends").add(hashMapOf( "username" to "","email" to "" )).await()

    private suspend fun createBlockedUsers(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection("blockedUsers").add(hashMapOf( "username" to "","email" to "" )).await()

    private suspend fun createCustomList(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection("customList").add(
                hashMapOf(
                    "nameList" to "",
                    "descriptions" to "0",
                    "like" to 0,
                    "dislike" to 0,
                    "access" to false
                )).await()

    suspend fun checkUserName(){

    }

    fun removeUser(){
    }

    fun editUser(){
    }
}