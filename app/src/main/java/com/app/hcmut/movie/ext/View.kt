package com.app.hcmut.movie.ext

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams

fun View.setMarginTop(value: Int) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        topMargin = value
    }
}

