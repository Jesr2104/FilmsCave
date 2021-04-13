package com.justjump.filmscave.data.datasources.users.remote

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.justjump.filmscave.domain._utils.ResultAuth
import com.justjump.filmscave.domain.users.UserValidationDataModel
import kotlinx.coroutines.tasks.await

class UsersFirebaseAuthDataSource {

    // instance of the firebase to implement all the solutions
    private var databaseInstance: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signUpUser(userValidationDataModel: UserValidationDataModel): ResultAuth {
        return try {
            databaseInstance.createUserWithEmailAndPassword(
                userValidationDataModel.email.trim(),
                userValidationDataModel.password.trim()
            ).await()
            ResultAuth(true)
        } catch (e: FirebaseAuthException) {ResultAuth(false, e.errorCode)}
    }

    suspend fun signUpUserGoogle(account: GoogleSignInAccount): ResultAuth{
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            databaseInstance.signInWithCredential(credential).await()
            ResultAuth(true)
        } catch (e: FirebaseAuthException) {ResultAuth(false, e.errorCode)}
    }

    suspend fun logInUser(userValidationDataModel: UserValidationDataModel): ResultAuth {
        return try {
            databaseInstance.signInWithEmailAndPassword(
                userValidationDataModel.email.trim(),
                userValidationDataModel.password.trim()
            ).await()
            ResultAuth(true)
        } catch (e: FirebaseAuthException) {ResultAuth(false, e.errorCode)}
    }

    suspend fun sendPasswordResetEmail(userValidationDataModel: UserValidationDataModel): ResultAuth{
        return try {
            databaseInstance.sendPasswordResetEmail(
                userValidationDataModel.email).await()
            ResultAuth(true)
        } catch (e: FirebaseAuthException){ ResultAuth(false, e.errorCode) }
    }
}