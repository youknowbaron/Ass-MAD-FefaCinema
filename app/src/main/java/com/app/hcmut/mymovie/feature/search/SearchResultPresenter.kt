package com.app.hcmut.mymovie.feature.search

import com.app.hcmut.mymovie.api.Api
import com.app.hcmut.mymovie.model.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultPresenter(val view: ISearchResult.View) : ISearchResult.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun searchMovie(query: String, page: Int) {
        view.showLoading()
        Api.createService().searchMovies(query, page).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                view.hideLoading()
                if (response.body() != null) {
                    view.onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.hideLoading()
                view.onFailure()
            }
        })
    }
}