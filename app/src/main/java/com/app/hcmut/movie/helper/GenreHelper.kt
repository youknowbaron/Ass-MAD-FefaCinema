package com.app.hcmut.movie.helper

import com.app.hcmut.movie.model.Genre

object GenreHelper {
    private val genresInVietnamese = hashMapOf(
        Pair(28, "Hành Động"),
        Pair(12, "Phiêu Lưu"),
        Pair(16, "Hoạt Hình"),
        Pair(35, "Hài"),
        Pair(80, "Hình Sự"),
        Pair(99, "Tài Liệu"),
        Pair(18, "Chính Kịch"),
        Pair(10751, "Gia Đình"),
        Pair(14, "Giả Tưởng"),
        Pair(36, "Lịch Sử"),
        Pair(27, "Kinh Dị"),
        Pair(10402, "Nhạc"),
        Pair(9648, "Bí Ẩn"),
        Pair(10749, "Lãng Mạn"),
        Pair(878, "Khoa Học Viễn Tưởng"),
        Pair(10770, "Chương Trình Truyền Hình"),
        Pair(53, "Gây Cấn"),
        Pair(10752, "Chiến Tranh"),
        Pair(37, "Miền Tây")
    )

    private val genres = hashMapOf(
        Pair(28, "Action"),
        Pair(12, "Adventure"),
        Pair(16, "Animation"),
        Pair(35, "Comedy"),
        Pair(80, "Crime"),
        Pair(99, "Documentary"),
        Pair(18, "Drama"),
        Pair(10751, "Family"),
        Pair(14, "Fantasy"),
        Pair(36, "History"),
        Pair(27, "Horror"),
        Pair(10402, "Music"),
        Pair(9648, "Mystery"),
        Pair(10749, "Romance"),
        Pair(878, "Science Fiction"),
        Pair(10770, "TV Movie"),
        Pair(53, "Thriller"),
        Pair(10752, "War"),
        Pair(37, "Western")
    )


    private fun getGenreFromId(id: Int?): String? = genres[id]

    fun getGenresFromIds(genresIds: List<Int>?): String {
        if (genresIds?.isEmpty() == true) return ""
        val sb = StringBuilder()
        for (i in 0 until genresIds?.size!!) {
            val genre = getGenreFromId(genresIds[i])
            sb.append(genre)
            if (i != genresIds.size - 1)
                sb.append(", ")
            else sb.append(".")
        }
        return sb.toString()
    }

    fun getGenres(genres: List<Genre>?): String {
        if (genres?.isEmpty() == true) return ""
        val sb = StringBuilder()
        for (i in 0 until genres?.size!!) {
            val genre = genres[i].name
            sb.append(genre)
            if (i != genres.size - 1)
                sb.append(" | ")
            else sb.append(".")
        }
        return sb.toString()
    }
}