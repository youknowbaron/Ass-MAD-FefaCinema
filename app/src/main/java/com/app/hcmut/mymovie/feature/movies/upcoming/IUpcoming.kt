package com.app.hcmut.mymovie.feature.movies.upcoming

import com.app.hcmut.mymovie.model.Movie

interface IUpcoming {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: MutableList<Movie>?)

        fun onFailure()
    }

    interface Presenter {
        fun getUpcoming(page: Int = 1)
    }
}