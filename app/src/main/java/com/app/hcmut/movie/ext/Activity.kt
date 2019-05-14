package com.app.hcmut.movie.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.app.hcmut.movie.util.MessageDialog

fun Activity.showMessage(msg: String, title: String = "") {
    MessageDialog(this, title, msg, false).show()
}

fun Activity.showMessageHasChoose(title: String, msg: String, clickYes: () -> Unit) {
    MessageDialog(this, title, msg, true)
        .setClickYes {
            clickYes.invoke()
        }
        .show()
}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}