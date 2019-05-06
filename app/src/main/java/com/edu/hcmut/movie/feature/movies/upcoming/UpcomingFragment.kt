package com.edu.hcmut.movie.feature.movies.upcoming

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.feature.movies.adapter.MovieAdapter
import com.edu.hcmut.movie.feature.movies.adapter.MovieViewPool
import com.edu.hcmut.movie.model.Movie
import kotlinx.android.synthetic.main.fragment_movies.*

class UpcomingFragment : Fragment(), IUpcoming.View {

    private var presenter: IUpcoming.Presenter
    private var adapter: MovieAdapter? = null

    init {
        presenter = UpcomingPresenter(this)
    }

    companion object {
        fun newInstance(): UpcomingFragment {
            return UpcomingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getUpcoming()
        adapter = MovieAdapter(context)
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
            adapter = this@UpcomingFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setRecycledViewPool(MovieViewPool.getInstance())
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
        }
    }

    override fun setPresenter(presenter: IUpcoming.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: List<Movie>?) {
        if (movies == null) return
        adapter?.setData(movies)
    }

    override fun onFailure() {
    }
}