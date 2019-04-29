package com.edu.hcmut.movie

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true)
    }

}