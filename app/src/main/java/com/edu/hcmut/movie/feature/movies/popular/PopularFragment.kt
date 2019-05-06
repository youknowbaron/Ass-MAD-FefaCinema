package com.edu.hcmut.movie.feature.movies.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.feature.detail.DetailMovieActivity
import com.edu.hcmut.movie.feature.movies.adapter.MovieAdapter
import com.edu.hcmut.movie.feature.movies.adapter.MovieViewPool
import com.edu.hcmut.movie.feature.movies.popular.IPopular
import com.edu.hcmut.movie.feature.movies.popular.PopularFragment
import com.edu.hcmut.movie.feature.movies.popular.PopularPresenter
import com.edu.hcmut.movie.model.Movie
import kotlinx.android.synthetic.main.fragment_movies.*

class PopularFragment : androidx.fragment.app.Fragment(), IPopular.View {

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
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            setRecycledViewPool(MovieViewPool.getInstance())
            (layoutManager as androidx.recyclerview.widget.LinearLayoutManager).recycleChildrenOnDetach = true
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