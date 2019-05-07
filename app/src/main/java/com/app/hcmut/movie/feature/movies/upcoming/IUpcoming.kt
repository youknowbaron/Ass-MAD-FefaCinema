package com.app.hcmut.movie.feature.movies.upcoming

import com.app.hcmut.movie.model.Movie

interface IUpcoming {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: List<Movie>?)

        fun onFailure()
    }

    interface Presenter {
        fun getUpcoming(page: Int = 1)
    }
}