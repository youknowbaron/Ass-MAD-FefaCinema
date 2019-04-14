package com.edu.hcmut.movie.feature.movies.now

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.model.Movie

class NowPlayingFragment : Fragment(), INowPlaying.View {

    private var presenter: INowPlaying.Presenter

    init {
        presenter = NowPlayingPresenter(this)
    }

    companion object {
        fun newInstance(): NowPlayingFragment {
            return NowPlayingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setPresenter(presenter: INowPlaying.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: List<Movie>?) {
    }

    override fun onFailure() {
    }
}
