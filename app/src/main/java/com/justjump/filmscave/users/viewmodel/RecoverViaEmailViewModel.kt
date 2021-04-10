package com.justjump.filmscave.users.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.filmscave.users.RecoverViaEmailFragment

class RecoverViaEmailViewModel: ViewModel() {

    interface Message{ fun showMessage(message: Int, success: Boolean, fieldError: Int) }

    var emailValue = MutableLiveData<String>()

    fun resetPassword(recoverViaEmailFragment: RecoverViaEmailFragment) {

    }
}