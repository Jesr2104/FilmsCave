package com.justjump.filmscave.data.datasources.users.remote

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.justjump.filmscave.domain.users.FriendDataModel
import com.justjump.filmscave.domain.users.UserStructureDataModel
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Suppress("UNCHECKED_CAST")
class UsersFirebaseDataSource {

    companion object{
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_USERNAME = "usernames"

        private const val COLLECTION_FRIENDS = "friends"
        private const val COLLECTION_CUSTOM_LIST = "customList"
        private const val COLLECTION_BLOCKED_USERS = "blockedUsers"
        private const val COLLECTION_INVITATIONS_FRIENDS = "friendRequests"

        private const val INFO_SUCCESSFUL = 0
        private const val INFO_USER_NOT_EXIST = 1
        private const val INFO_REQUEST_ALREADY_DONE = 100

    }

    // instance of the firebase to implement all the solutions
    private var databaseInstance: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun checkUserName(Username: String): Boolean {
        var usernameUnique = true
        return try {
            val usernameList = databaseInstance.collection(COLLECTION_USERNAME).get().await()
            for (item in  usernameList.documents) {
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
            "typeUser" to userStructureDataModel.typeUser
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

            UserStructureDataModel(
                userName = getUsername(email),
                email = usersDataFireStore.get("email") as String,
                avatar = usersDataFireStore.get("avatar") as String,
                date = usersDataFireStore.get("date") as String,
                setting = usersDataFireStore.get("setting") as HashMap<String, String>,
                typeUser = usersDataFireStore.get("typeUser") as String,
                friends = getFriendsList(email)
            )
        } catch (e: FirebaseException){
            UserStructureDataModel()
        }
    }

    suspend fun inviteFriend(friend: String, userStructure: FriendDataModel): Int {
        val friendDataModel = FriendDataModel()
        var result: Boolean
        try {
            val usernameList = databaseInstance.collection(COLLECTION_USERNAME).get().await()
            for (item in  usernameList.documents) {
                if (item.get("username").toString().trim().lowercase() == friend.lowercase()){
                    friendDataModel.Username = item.get("username").toString()
                    friendDataModel.Email = item.id

                    // create the user invitation
                    result = createFriendInvitation(friendDataModel, userStructure)
                    return if (!result) { INFO_REQUEST_ALREADY_DONE }
                    else { INFO_SUCCESSFUL }
                }
            }
        } catch (e: Exception){
            // if missing you capture the exception
        }
        return INFO_USER_NOT_EXIST
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
            .collection(COLLECTION_FRIENDS).add(hashMapOf("username" to "", "email" to "")).await()

    private suspend fun createBlockedUsers(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection(COLLECTION_BLOCKED_USERS).add(hashMapOf("username" to "", "email" to "")).await()

    private suspend fun createCustomList(email: String) =
        databaseInstance.collection(COLLECTION_USERS).document(email)
            .collection(COLLECTION_CUSTOM_LIST).add(hashMapOf("nameList" to "", "descriptions" to "0", "like" to 0, "dislike" to 0, "access" to false)).await()

    private suspend fun createFriendInvitation(friend: FriendDataModel, userStructure: FriendDataModel): Boolean{
        // user information
        val userInvitation = hashMapOf(
            "username" to userStructure.Username,
            "email" to userStructure.Email,
            "date" to Calendar.getInstance().time.toString()
        )

        val requestList = databaseInstance.collection(COLLECTION_USERS).document(friend.Email).collection(
            COLLECTION_INVITATIONS_FRIENDS).get().await()

        // if exist is true: you don't have previous friend request
        var exist = true
        for (item in  requestList.documents) {
            if (item.get("email").toString().trim() == userStructure.Email){
                // if you fiend the email, it's because you have a previous friend request and you don't need to send a new one
                exist = false
            }
        }

        if (exist){
            databaseInstance.collection(COLLECTION_USERS).document(friend.Email)
                .collection(COLLECTION_INVITATIONS_FRIENDS).add(userInvitation).await()
            return true
        }
        return false
    }

    private suspend fun getFriendsList(email: String): ArrayList<FriendDataModel> {
        val usersDataFriends = databaseInstance.collection(COLLECTION_USERS).document(email.trim()).collection(
            COLLECTION_FRIENDS).get().await()

        var count = 0
        val friends: ArrayList<FriendDataModel> = arrayListOf()
        while (usersDataFriends.size() > count){
            friends.add(
                FriendDataModel(
                    usersDataFriends.documents[count].get("email") as String,
                    usersDataFriends.documents[count].get("username") as String
                ))
            count++
        }
        return friends
    }

    //********************************************************//
    //          Functions to create Collections
    //********************************************************//
    private suspend fun getUsername(email: String) =
        databaseInstance.collection(COLLECTION_USERNAME).document(email.trim()).get().await().get("username") as String
}