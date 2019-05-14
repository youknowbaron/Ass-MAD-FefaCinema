package com.app.hcmut.movie.feature.detail

import com.app.hcmut.movie.api.Api
import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.model.Movies
import com.app.hcmut.movie.model.Videos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailPresenter(val view: IDetail.View) : IDetail.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getMovieDetail(movieId: Int) {
        view.showLoading()
        Api.createService().getDetails(movieId).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                view.hideLoading()
                if (response.body() != null) {
                    view.onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                view.hideLoading()
                view.onFailure()
            }
        })
    }

    override fun getVideoTrailer(movieId: Int) {
        Api.createService().getVideoTrailer(movieId).enqueue(object : Callback<Videos> {
            override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                view.hideLoading()
                if (response.body() != null) {
                    view.onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Videos>, t: Throwable) {
                view.hideLoading()
                view.onFailure()
            }
        })
    }

    override fun getRecommendations(movieId: Int) {
        Api.createService().getRecommendations(movieId).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                view.hideLoading()
                if (response.body() != null) {
                    view.onResponse(response.body()?.results ?: mutableListOf())
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.hideLoading()
                view.onFailure()
            }
        })
    }
}