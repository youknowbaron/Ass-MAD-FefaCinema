package com.app.hcmut.movie.feature.detail

import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.model.Videos

interface IDetail {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movie: Movie)

        fun onResponse(video: Videos)

        fun onResponse(movies: MutableList<Movie>)

        fun onFailure()

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun getMovieDetail(movieId: Int)
        fun getVideoTrailer(movieId: Int)
        fun getRecommendations(movieId: Int)
    }
}