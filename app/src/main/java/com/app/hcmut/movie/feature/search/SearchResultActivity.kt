package com.app.hcmut.movie.feature.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hcmut.movie.R
import com.app.hcmut.movie.ext.gone
import com.app.hcmut.movie.ext.visible
import com.app.hcmut.movie.feature.BaseActivity
import com.app.hcmut.movie.feature.detail.DetailMovieActivity
import com.app.hcmut.movie.feature.movies.adapter.MovieAdapter
import com.app.hcmut.movie.feature.movies.adapter.MovieViewPool
import com.app.hcmut.movie.model.Movies
import com.app.hcmut.movie.util.EndlessRecyclerViewScrollListener
import com.app.hcmut.movie.util.QUERY
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : BaseActivity(), ISearchResult.View {

    companion object {
        fun newInstance(context: Context?, query: String): Intent {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra(QUERY, query)
            return intent
        }
    }

    private var presenter: ISearchResult.Presenter
    private var adapter: MovieAdapter? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var nextPage = 1
    private var query = ""

    init {
        presenter = SearchResultPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        query = intent.getStringExtra(QUERY)
        adapter = MovieAdapter(this) {
            startActivity(DetailMovieActivity.newInstance(this, it))
        }
        initToolbar()
        initView()
        presenter.searchMovie(query)
    }

    private fun initToolbar() {
        icBack.setOnClickListener { onBackPressed() }
    }

    private fun initView() {
        rcvMovies.apply {
            adapter = this@SearchResultActivity.adapter
            layoutManager = LinearLayoutManager(context)
            setRecycledViewPool(MovieViewPool.getInstance())
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
            scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    nextPage++
                    presenter.searchMovie(query, nextPage)
                }
            }
            addOnScrollListener(scrollListener!!)
        }
    }

    override fun setPresenter(presenter: ISearchResult.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movies: Movies) {
        if (movies.results == null) return
        tvResult.text = getString(R.string.search_results, movies.totalResults, query)
        if (movies.totalResults == 0) {
            rcvMovies.gone()
            lavNoResults.visible()
        } else {
            adapter?.addAll(movies.results)
        }
    }

    override fun onFailure() {}
}