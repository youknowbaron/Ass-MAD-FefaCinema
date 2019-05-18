package com.app.hcmut.mymovie.feature.movies.now

import com.app.hcmut.mymovie.api.Api
import com.app.hcmut.mymovie.model.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingPresenter(val view: INowPlaying.View) :
    INowPlaying.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getNowPlaying(page: Int) {
        view.showLoading()
        Api.createService().getNowPlaying(page).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                view.hideLoading()
                if (response.body() != null) {
                    view.onResponse(response.body()?.results)
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.hideLoading()
                view.onFailure()
            }
        })
    }
}