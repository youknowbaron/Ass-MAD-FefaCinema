package com.app.hcmut.mymovie.feature.movies.now

import com.app.hcmut.mymovie.model.Movie

interface INowPlaying {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: MutableList<Movie>?)

        fun onFailure()

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun getNowPlaying(page: Int = 1)
    }
}