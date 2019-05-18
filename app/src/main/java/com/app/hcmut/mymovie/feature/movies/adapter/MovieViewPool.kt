package com.app.hcmut.mymovie.feature.movies.adapter

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