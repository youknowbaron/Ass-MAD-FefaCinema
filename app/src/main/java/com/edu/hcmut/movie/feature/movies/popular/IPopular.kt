package com.edu.hcmut.movie.feature.movies.popular

import com.edu.hcmut.movie.model.Movie

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