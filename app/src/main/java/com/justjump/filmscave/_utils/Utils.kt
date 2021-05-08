package com.justjump.filmscave._utils

import androidx.fragment.app.FragmentActivity

fun String.validatePassword() = this.length >= 6

fun changeColorStatusBar(activity: FragmentActivity, idColor: Int) {
    activity.window.statusBarColor = activity.getColor(idColor)
}
