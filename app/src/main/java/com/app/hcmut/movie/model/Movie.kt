package com.app.hcmut.movie.model

import com.google.gson.annotations.SerializedName

class Movie {

    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    var budget: Int? = null

    @SerializedName("genres")
    var genres: List<Genre>? = null

    var homepage: String? = null

    var id: Int? = null

    @SerializedName("imdb_id")
    var imdbId: String? = null

    @SerializedName("original_title")
    var originalTitle: String? = null

    var overview: String? = null

    var popularity: Double? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    var revenue: Long? = null

    @SerializedName("runtime")
    var runtime: Int? = null

    var status: String? = null

    @SerializedName("tagline")
    var tagline: String? = null

    var title: String? = null

    var video: Boolean? = null

    @SerializedName("vote_average")
    var voteAverage: Double? = null

    @SerializedName("vote_count")
    var voteCount: Int? = null

    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null

}
