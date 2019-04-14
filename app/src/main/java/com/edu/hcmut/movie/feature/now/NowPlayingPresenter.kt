package com.edu.hcmut.movie.feature.now

import com.edu.hcmut.movie.api.Api
import com.edu.hcmut.movie.model.NowPlaying
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingPresenter(val view: INowPlaying.View) : INowPlaying.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getNowPlaying(page: Int) {
        Api.createService().getNowPlaying().enqueue(object : Callback<NowPlaying> {
            override fun onResponse(call: Call<NowPlaying>, response: Response<NowPlaying>) {
                if (response.body() != null) {
                    view.onResponse(response.body()?.results)
                }
            }

            override fun onFailure(call: Call<NowPlaying>, t: Throwable) {
                view.onFailure()
            }
        })
    }
}