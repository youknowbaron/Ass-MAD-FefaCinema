package com.edu.hcmut.movie.feature.now

import com.edu.hcmut.movie.model.Movie

interface INowPlaying {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: List<Movie>?)

        fun onFailure()
    }

    interface Presenter {
        fun getNowPlaying(page: Int = 1)
    }
}