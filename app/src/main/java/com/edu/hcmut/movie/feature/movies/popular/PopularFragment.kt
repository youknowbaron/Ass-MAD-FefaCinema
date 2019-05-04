package com.edu.hcmut.movie.feature.movies.popular

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

class PopularFragment : Fragment(), IPopular.View {

    private var presenter: IPopular.Presenter
    private var adapter: MovieAdapter? = null

    init {
        presenter = PopularPresenter(this)
    }

    companion object {
        fun newInstance(): PopularFragment {
            return PopularFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getPopular()
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
            adapter = this@PopularFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun setPresenter(presenter: IPopular.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: List<Movie>?) {
        if (movies == null) return
        adapter?.setData(movies)
    }

    override fun onFailure() {
    }
}