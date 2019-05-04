package com.edu.hcmut.movie.feature.movies.now

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.feature.detail.DetailMovieActivity
import com.edu.hcmut.movie.feature.movies.adapter.MovieAdapter
import com.edu.hcmut.movie.model.Movie
import kotlinx.android.synthetic.main.fragment_movies.*

class NowPlayingFragment : Fragment(), INowPlaying.View {

    private var presenter: INowPlaying.Presenter
    private var adapter: MovieAdapter? = null
    private var fireStore: FirebaseFirestore

    init {
        presenter = NowPlayingPresenter(this)
        fireStore = FirebaseFirestore.getInstance()
    }

    companion object {
        fun newInstance(): NowPlayingFragment {
            return NowPlayingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getNowPlaying()
        adapter = MovieAdapter(context) {
            startActivity(DetailMovieActivity.newInstance(context, it))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rcvMovies.apply {
            adapter = this@NowPlayingFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun setPresenter(presenter: INowPlaying.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: List<Movie>?) {
        if (movies == null) return
        adapter?.setData(movies)
    }

    override fun onFailure() {
    }
}
