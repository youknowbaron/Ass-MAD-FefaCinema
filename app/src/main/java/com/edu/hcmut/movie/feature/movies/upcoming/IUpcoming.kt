package com.edu.hcmut.movie.feature.movies.upcoming

import com.edu.hcmut.movie.model.Movie

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