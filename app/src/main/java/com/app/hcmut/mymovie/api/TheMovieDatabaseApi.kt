package com.app.hcmut.mymovie.api

import com.app.hcmut.mymovie.model.Movie
import com.app.hcmut.mymovie.model.Movies
import com.app.hcmut.mymovie.model.Videos
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

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String?, @Query("page") page: Int = 1): Call<Movies>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendations(
        @Path("movie_id") movieId: Int?,
        @Query("page") page: Int = 1
    ): Call<Movies>
}