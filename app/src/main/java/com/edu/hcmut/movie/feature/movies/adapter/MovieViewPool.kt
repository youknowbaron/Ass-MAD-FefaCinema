package com.edu.hcmut.movie.feature.movies.adapter

import android.support.v7.widget.RecyclerView

class MovieViewPool {
    companion object {
        private val LOCK = Any()
        private var instance: RecyclerView.RecycledViewPool? = null
        fun getInstance(): RecyclerView.RecycledViewPool {
            synchronized(LOCK) {
                if (instance == null)
                    instance = RecyclerView.RecycledViewPool()
                return instance!!
            }
        }
    }
}