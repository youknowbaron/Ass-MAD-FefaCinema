package com.app.hcmut.movie.feature

import androidx.appcompat.app.AppCompatActivity
import com.roger.catloadinglibrary.CatLoadingView

abstract class BaseActivity : AppCompatActivity() {
    private var loadingFragment: CatLoadingView? = null
    private var isLoading = false
    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            loadingFragment = CatLoadingView()
            loadingFragment?.show(supportFragmentManager, "")
        }
    }

    fun hideLoading() {
        if (loadingFragment != null && isLoading) {
            isLoading = false
            loadingFragment?.dismiss()
        }
    }
}