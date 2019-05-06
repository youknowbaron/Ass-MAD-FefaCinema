package com.edu.hcmut.movie.feature.movies.adapter

import androidx.recyclerview.widget.RecyclerView

class MovieViewPool {
    companion object {
        private val LOCK = Any()
        private var instance: androidx.recyclerview.widget.RecyclerView.RecycledViewPool? = null
        fun getInstance(): androidx.recyclerview.widget.RecyclerView.RecycledViewPool {
            synchronized(LOCK) {
                if (instance == null)
                    instance = androidx.recyclerview.widget.RecyclerView.RecycledViewPool()
                return instance!!
            }
        }
    }
}