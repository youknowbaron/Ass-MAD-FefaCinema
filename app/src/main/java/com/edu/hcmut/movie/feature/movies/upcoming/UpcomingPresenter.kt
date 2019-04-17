package com.edu.hcmut.movie.feature.movies.upcoming

import com.edu.hcmut.movie.api.Api
import com.edu.hcmut.movie.model.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingPresenter(val view: IUpcoming.View) :
    IUpcoming.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getUpcoming(page: Int) {
        Api.createService().getUpcoming().enqueue(object : Callback<Movies> {
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