package com.justjump.filmscave.users.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.R
import com.justjump.filmscave.data._utils.Status
import com.justjump.filmscave.data.datasources.users.ResetPassViaEmail
import com.justjump.filmscave.data.repositories.users.ResetPasswordEmailRepository
import com.justjump.filmscave.domain.users.UserValidationDataModel
import com.justjump.filmscave.usecases.users.ResetPasswordEmailUseCases
import com.justjump.filmscave.users.RecoverViaEmailFragment

class RecoverViaEmailViewModel: ViewModel() {

    interface Message{ fun showMessage(message: Int, success: Boolean, fieldError: Int) }

    var emailValue = MutableLiveData<String>()

    fun resetPassword(recoverViaEmailFragment: RecoverViaEmailFragment) =
        ResetPasswordEmailUseCases(ResetPasswordEmailRepository(ResetPassViaEmail()))
            .invoke(createUserValidation()).observeForever {
                when (it.status) {
                    Status.SUCCESS -> {
                        recoverViaEmailFragment.showMessage(R.string.id_message_send_reset_correct, true, 0)
                    }
                    Status.ERROR -> {
                        when (it.codeException){
                            "ERROR_INVALID_EMAIL" -> { recoverViaEmailFragment.showMessage(R.string.id_message_email_invalid, false,1)}
                            "ERROR_USER_NOT_FOUND" -> { recoverViaEmailFragment.showMessage(R.string.id_message_email_not_registered, false,1)}
                        }
                    }}}

    private fun createUserValidation() = UserValidationDataModel(emailValue.value.toString().trim(), "")
}