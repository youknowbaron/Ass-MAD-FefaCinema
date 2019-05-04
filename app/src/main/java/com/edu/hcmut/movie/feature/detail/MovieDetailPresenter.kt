package com.edu.hcmut.movie.feature.detail

import com.edu.hcmut.movie.api.Api
import com.edu.hcmut.movie.model.Movie
import com.edu.hcmut.movie.model.Videos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailPresenter(val view: IDetail.View) : IDetail.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getMovieDetail(movieId: Int) {
        Api.createService().getDetails(movieId).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.body() != null) {
                    view.onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                view.onFailure()
            }
        })
    }

    override fun getVideoTrailer(movieId: Int) {
        Api.createService().getVideoTrailer(movieId).enqueue(object : Callback<Videos> {
            override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                if (response.body() != null) {
                    view.onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Videos>, t: Throwable) {
                view.onFailure()
            }
        })
    }
}