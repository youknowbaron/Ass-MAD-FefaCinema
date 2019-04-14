package com.edu.hcmut.movie.feature.now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.model.Movie

class NowPlayingActivity : AppCompatActivity(), INowPlaying.View {

    private var presenter: INowPlaying.Presenter

    init {
        presenter = NowPlayingPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        presenter.getNowPlaying()
    }

    override fun setPresenter(presenter: INowPlaying.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: List<Movie>?) {
        Log.d("NowPlayingActivity", "onResponse")
    }

    override fun onFailure() {
        Log.d("NowPlayingActivity", "onFailure")
    }
}
