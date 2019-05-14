package com.app.hcmut.movie.feature.movies.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hcmut.movie.R
import com.app.hcmut.movie.feature.detail.DetailMovieActivity
import com.app.hcmut.movie.feature.movies.adapter.MovieAdapter
import com.app.hcmut.movie.feature.movies.adapter.MovieViewPool
import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.util.EndlessRecyclerViewScrollListener
import com.app.hcmut.movie.util.MessageDialog
import kotlinx.android.synthetic.main.fragment_movies.*

class UpcomingFragment : Fragment(), IUpcoming.View {

    private var presenter: IUpcoming.Presenter
    private var adapter: MovieAdapter? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var nextPage = 1

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
            adapter = this@UpcomingFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setRecycledViewPool(MovieViewPool.getInstance())
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
            scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    nextPage++
                    presenter.getUpcoming(nextPage)
                }
            }
            addOnScrollListener(scrollListener!!)
        }
    }

    override fun setPresenter(presenter: IUpcoming.Presenter) {
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