package com.justjump.filmscave.users.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.justjump.filmscave.R
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.users.SignUp
import com.justjump.filmscave.data.repositories.users.SignUpRepository
import com.justjump.filmscave.domain.users.UserStructureDataModel
import com.justjump.filmscave.domain.users.UserValidationDataModel
import com.justjump.filmscave.framework.room.users.RoomDataSource
import com.justjump.filmscave.usecases.users.SignUpUseCases
import com.justjump.filmscave.users.SignUpFragment

class SignUpViewModel : ViewModel() {

    interface Message{ fun showMessage(message: Int, success: Boolean, fieldError: Int) }

    var userNameValue = MutableLiveData<String>()
    var emailValue = MutableLiveData<String>()
    var passwordValue = MutableLiveData<String>()

    fun signUpUser(signUpFragment: SignUpFragment, appContext: Context)
        = SignUpUseCases(SignUpRepository(SignUp(RoomDataSource())))
        .invoke(appContext, createUserValidation(),createUserStructureDataModel()).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    signUpFragment.showMessage(R.string.id_message_sign_up_successful,true, 0)
                }
                Status.ERROR -> {
                    when (it.codeException){
                        "ERROR_INVALID_EMAIL" ->{ signUpFragment.showMessage(R.string.id_message_address_badly_formatted, false,2) }
                        "ERROR_EMAIL_ALREADY_IN_USE" ->{ signUpFragment.showMessage(R.string.id_message_email_used, false, 2) }
                        "ERROR_USERNAME_NOT_ABLE" ->{signUpFragment.showMessage(R.string.id_message_username_false,false, 1)}
                    }}}}

    fun signUpUserGoogle(signUpFragment: SignUpFragment, appContext: Context, account: GoogleSignInAccount, task: Task<GoogleSignInAccount>)
        = SignUp(RoomDataSource()).signUpGoogle(appContext, account,
            UserStructureDataModel( userName = task.result!!.email!!, email = task.result!!.email!!, avatar = task.result!!.photoUrl!!.toString()) ).observeForever{
            when (it.status) {
                Status.SUCCESS -> {
                    signUpFragment.showMessage(R.string.id_message_sign_up_successful,true, 0)
                }
                Status.ERROR -> {
                    when (it.codeException){
                        "ERROR_EMAIL_ALREADY_IN_USE" -> {signUpFragment.showMessage(R.string.id_message_email_used, false, 3)}
                    }}}}

    fun signUpFacebook(signUpFragment: SignUpFragment,appContext: Context,token: AccessToken,emailFromTokenFacebook: String)
        = SignUp(RoomDataSource()).signUpFacebook( appContext, token, emailFromTokenFacebook,
            createUserStructureDataModel()).observeForever{
                when (it.status) {
                    Status.SUCCESS -> {
                        signUpFragment.showMessage(R.string.id_message_sign_up_successful, true, 0)
                    }
                    Status.ERROR -> {
                        when (it.codeException) {
                            "ERROR_EMAIL_ALREADY_IN_USE" -> {signUpFragment.showMessage(R.string.id_message_email_used, false, 3)}
                        }}}}

    private fun createUserValidation() = UserValidationDataModel(emailValue.value.toString(), passwordValue.value.toString())

    private fun createUserStructureDataModel() = UserStructureDataModel(userName = userNameValue.value.toString(),email = emailValue.value.toString())
}

/*      Firebase -> Exceptions
("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
("ERROR_WEAK_PASSWORD", "The given password is invalid."));
("ERROR_MISSING_EMAIL", "An email address must be provided.";*/

//      Personal -> Exceptions
// "ERROR_USERNAME_NOT_ABLE" , "has already been taken."