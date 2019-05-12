package com.app.hcmut.movie.util

object Utils {
    fun getRunTime(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "${hours}h ${minutes}m"
    }
}