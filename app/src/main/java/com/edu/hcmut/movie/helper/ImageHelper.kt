package com.edu.hcmut.movie.helper

class ImageHelper {
    companion object {
        private const val BASE_URL = "https://image.tmdb.org/t/p"
        const val HIGH_QUALITY = "/w400"
        const val NORMAL_QUALITY = "/w200"

        fun getLinkImage(path: String?, quality: String): String {
            return "$BASE_URL$quality$path"
        }
    }
}