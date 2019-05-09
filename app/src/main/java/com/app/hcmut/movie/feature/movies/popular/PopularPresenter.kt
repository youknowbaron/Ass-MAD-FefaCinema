package com.app.hcmut.movie.feature.movies.popular

import com.app.hcmut.movie.api.Api
import com.app.hcmut.movie.model.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularPresenter(val view: IPopular.View) :
    IPopular.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getPopular(page: Int) {
        Api.createService().getPopular(page).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.body() != null) {
                    view.onResponse(response.body()?.results)
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.onFailure()
            }
        })
    }
}