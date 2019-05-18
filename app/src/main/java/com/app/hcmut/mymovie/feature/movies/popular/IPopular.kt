package com.app.hcmut.mymovie.feature.movies.popular

import com.app.hcmut.mymovie.model.Movie

interface IPopular {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: MutableList<Movie>?)

        fun onFailure()
    }

    interface Presenter {
        fun getPopular(page: Int = 1)
    }
}