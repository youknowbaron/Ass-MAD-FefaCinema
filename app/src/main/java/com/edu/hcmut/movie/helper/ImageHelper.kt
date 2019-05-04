package com.edu.hcmut.movie.helper

object ImageHelper {
    private const val BASE_URL = "https://image.tmdb.org/t/p"

    fun getLinkImage(path: String?, quality: String): String {
        return "$BASE_URL$quality$path"
    }
}