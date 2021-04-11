package com.justjump.filmscave.data.datasources.users.remote

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.justjump.filmscave.domain.users.UserStructureDataModel
import kotlinx.coroutines.tasks.await

@Suppress("UNCHECKED_CAST")
class UsersFirebaseDataSource {

    companion object{
        const val COLLECTION_USERS = "users"
        const val COLLECTION_USERNAME = "usernames"
    }

    // instance of the firebase to implement all the solutions
    private var databaseInstance: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun checkUserName(Username: String): Boolean {
        var usernameUnique = true
        return try {
            val a = databaseInstance.collection(COLLECTION_USERNAME).get().await()
            for (item in  a.documents) {
                if (item.get("username").toString().trim().equals(Username.trim(), ignoreCase = true)){
                    usernameUnique = false
                }
            }
            usernameUnique
        } catch (e: Exception){
            Log.e("Jesr2104","error")
            usernameUnique
        }
    }

    suspend fun insertUser(userStructureDataModel: UserStructureDataModel): Boolean{
        var result = false
        val userId = userStructureDataModel.email.trim()

        val setting = hashMapOf(
            "language" to "default"
        )

        // create the collection user
        createUsername(userStructureDataModel.userName, userStructureDataModel.email)

        // user information
        val user = hashMapOf(
            "email" to userStructureDataModel.email,
            "avatar" to userStructureDataModel.avatar,
            "date" to userStructureDataModel.date,
            "setting" to setting,
            "mobileNumber" to userStructureDataModel.mobileNumber,
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
            result
        }
    }

    suspend fun getUser(email: String): UserStructureDataModel {
        return try {
            val usersDataFireStore = databaseInstance.collection(COLLECTION_USERS).document(email.trim()).get().await()
            //TODO ("Load the complete data for the list of the user")
            // BlockedUsers
            // CustomList
            // Friends

            UserStructureDataModel(
                userName = getUsername(email),
                email = usersDataFireStore.get("email") as String,
                avatar = usersDataFireStore.get("avatar") as String,
                date = usersDataFireStore.get("date") as String,
                mobileNumber = usersDataFireStore.get("mobileNumber") as String,
                setting = usersDataFireStore.get("setting") as HashMap<String, Any>
            )
        } catch (e: FirebaseException){
            UserStructureDataModel()
        }
    }

    fun removeUser(){
        //TODO ("implement the function to delete user from firebase")
    }

    fun editUser(){
        //TODO (" implement the function to edit the information of the user")
    }

    //********************************************************//
    //          Functions to create Collections
    //********************************************************//
    private suspend fun createUsername(username: String, email: String) =
        databaseInstance.collection(COLLECTION_USERNAME).document(email.trim()).set(hashMapOf("username" to username)).await()

    private suspend fun createFriendsList(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection("friends").add(hashMapOf("username" to "", "email" to "")).await()

    private suspend fun createBlockedUsers(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection("blockedUsers").add(hashMapOf("username" to "", "email" to "")).await()

    private suspend fun createCustomList(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection("customList").add(
                hashMapOf(
                    "nameList" to "",
                    "descriptions" to "0",
                    "like" to 0,
                    "dislike" to 0,
                    "access" to false
                )
            ).await()

    //********************************************************//
    //          Functions to create Collections
    //********************************************************//
    private suspend fun getUsername(email: String) =
        databaseInstance.collection(COLLECTION_USERNAME).document(email.trim()).get().await().get("username") as String
}