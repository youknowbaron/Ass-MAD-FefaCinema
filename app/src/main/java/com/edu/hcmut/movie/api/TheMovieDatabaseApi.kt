package com.edu.hcmut.movie.api

import com.edu.hcmut.movie.model.Movie
import com.edu.hcmut.movie.model.Movies
import com.edu.hcmut.movie.model.Videos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDatabaseApi {

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int = 1): Call<Movies>

    @GET("movie/{movie_id}")
    fun getDetails(@Path("movie_id") movieId: Int?): Call<Movie>

    @GET("movie/{movie_id}/videos")
    fun getVideoTrailer(@Path("movie_id") movieId: Int?): Call<Videos>


    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int = 1): Call<Movies>

    @GET("movie/upcoming")
    fun getUpcoming(@Query("page") page: Int = 1): Call<Movies>

}