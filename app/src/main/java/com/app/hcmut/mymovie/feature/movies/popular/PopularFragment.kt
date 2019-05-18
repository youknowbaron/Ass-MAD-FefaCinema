package com.app.hcmut.mymovie.feature.movies.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hcmut.mymovie.R
import com.app.hcmut.mymovie.feature.detail.DetailMovieActivity
import com.app.hcmut.mymovie.feature.movies.adapter.MovieAdapter
import com.app.hcmut.mymovie.feature.movies.adapter.MovieViewPool
import com.app.hcmut.mymovie.model.Movie
import com.app.hcmut.mymovie.util.EndlessRecyclerViewScrollListener
import com.app.hcmut.mymovie.util.MessageDialog
import kotlinx.android.synthetic.main.fragment_movies.*

class PopularFragment : Fragment(), IPopular.View {

    private var presenter: IPopular.Presenter
    private var adapter: MovieAdapter? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var nextPage = 1

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
            setRecycledViewPool(MovieViewPool.getInstance())
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
            scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    nextPage++
                    presenter.getPopular(nextPage)
                }
            }
            addOnScrollListener(scrollListener!!)
        }
    }

    override fun setPresenter(presenter: IPopular.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: MutableList<Movie>?) {
        if (movies == null) return
        adapter?.addAll(movies)
    }

    override fun onFailure() {
//        MessageDialog(context ?: return, "Error", "No internet connection", false).show()
    }
}