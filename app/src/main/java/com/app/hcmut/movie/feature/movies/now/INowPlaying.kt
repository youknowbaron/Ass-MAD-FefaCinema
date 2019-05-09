package com.app.hcmut.movie.feature.movies.now

import com.app.hcmut.movie.model.Movie

interface INowPlaying {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: MutableList<Movie>?)

        fun onFailure()
    }

    interface Presenter {
        fun getNowPlaying(page: Int = 1)
    }
}