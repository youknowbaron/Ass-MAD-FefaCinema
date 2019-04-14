package com.edu.hcmut.movie.feature.now

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.model.Movie
import kotlinx.android.synthetic.main.activity_now_playing.*

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
        tv.text = "Response"
    }

    override fun onFailure() {
        tv.text = "Failure"
    }
}
