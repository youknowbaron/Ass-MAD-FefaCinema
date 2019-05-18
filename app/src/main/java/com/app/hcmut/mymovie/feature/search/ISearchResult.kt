package com.app.hcmut.mymovie.feature.search

import com.app.hcmut.mymovie.model.Movies

interface ISearchResult {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(movies: Movies)

        fun onFailure()

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun searchMovie(query: String, page: Int = 1)
    }
}