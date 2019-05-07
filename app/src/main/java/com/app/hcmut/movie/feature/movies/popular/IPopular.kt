package com.app.hcmut.movie.feature.movies.popular

import com.app.hcmut.movie.model.Movie

interface IPopular {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: List<Movie>?)

        fun onFailure()
    }

    interface Presenter {
        fun getPopular(page: Int = 1)
    }
}