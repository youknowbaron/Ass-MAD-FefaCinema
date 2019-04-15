package com.edu.hcmut.movie.helper

class GenreHelper {
    companion object {
        private val genres = hashMapOf(
            Pair(
                28,
                "Hành Động"
            ),
            Pair(
                12,
                "Phiêu Lưu"
            ),
            Pair(
                16,
                "Hoạt Hình"
            ),
            Pair(
                35,
                "Hài"
            ),
            Pair(
                80,
                "Hình Sự"
            ),
            Pair(
                99,
                "Tài Liệu"
            ),
            Pair(
                18,
                "Chính Kịch"
            ),
            Pair(
                10751,
                "Gia Đình"
            ),

            Pair(
                14,
                "Giả Tưởng"
            ),
            Pair(
                36,
                "Lịch Sử"
            ),
            Pair(
                27,
                "Kinh Dị"
            ),
            Pair(
                10402,
                "Nhạc"
            ),

            Pair(
                9648,
                "Bí Ẩn"
            ),

            Pair(
                10749,
                "Lãng Mạn"
            ),
            Pair(
                878,
                "Khoa Học Viễn Tưởng"
            ),
            Pair(
                10770,
                "Chương Trình Truyền Hình"
            ),
            Pair(
                53,
                "Gây Cấn"
            ),
            Pair(
                10752,
                "Chiến Tranh"
            ),
            Pair(
                37,
                "Miền Tây"
            )
        )

        fun getGenreFromId(id: Int): String? = genres[id]
    }
}