package com.app.hcmut.movie.model

import com.google.gson.annotations.SerializedName

class Movies {
    val results: MutableList<Movie>? = null

    val page: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null

    val dates: Dates? = null
}