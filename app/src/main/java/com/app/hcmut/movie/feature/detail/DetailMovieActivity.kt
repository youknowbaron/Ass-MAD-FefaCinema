package com.app.hcmut.movie.feature.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hcmut.movie.BuildConfig
import com.app.hcmut.movie.R
import com.app.hcmut.movie.ext.showMessage
import com.app.hcmut.movie.feature.detail.adapter.DetailAdapter
import com.app.hcmut.movie.helper.GenreHelper
import com.app.hcmut.movie.helper.ImageHelper
import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.model.Videos
import com.app.hcmut.movie.util.HIGH_QUALITY
import com.app.hcmut.movie.util.MOVIE_ID
import com.app.hcmut.movie.util.NORMAL_QUALITY
import com.app.hcmut.movie.util.Utils
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMovieActivity : YouTubeBaseActivity(), IDetail.View {

    private var presenter: IDetail.Presenter = MovieDetailPresenter(this)
    private var movieId: Int = -1
    private var idVideo: String? = null
    private var isFullScreen: Boolean = false
    private var youtubePlayer: YouTubePlayer? = null
    private var playerFragment: YouTubePlayerFragment? = null
    private lateinit var adapter: DetailAdapter

    companion object {
        fun newInstance(context: Context?, movieId: Int): Intent {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        movieId = intent.getIntExtra(MOVIE_ID, -1)
        adapter = DetailAdapter(this) {
            it.id?.let { it1 -> startActivity(DetailMovieActivity.newInstance(this, it1)) }
        }
        initRecyclerView()
        rvRecommendations.adapter = adapter
        rvRecommendations.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        presenter.getMovieDetail(movieId)
        presenter.getVideoTrailer(movieId)
        presenter.getRecommendations(movieId)

        initClickEvent()
        transparentStatusBar()
    }

    private fun transparentStatusBar() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun setPresenter(presenter: IDetail.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(movie: Movie) {
        if (!this.isDestroyed)
            setDataOnView(movie)
    }

    override fun onResponse(video: Videos) {
        if (video.results?.isEmpty() == true) {
            return
        }
        idVideo = video.results?.get(0)?.key
    }

    override fun onResponse(movies: MutableList<Movie>) {
        adapter.setData(movies)
    }

    @SuppressLint("SetTextI18n")
    private fun setDataOnView(movie: Movie) {
        Glide.with(this)
            .load(ImageHelper.getLinkImage(movie.backdropPath, HIGH_QUALITY))
            .into(ivVideo)
        Glide.with(this)
            .load(ImageHelper.getLinkImage(movie.posterPath, NORMAL_QUALITY))
            .into(ivPoster)
        tvTitle.text = movie.title
        if (!movie.genres.isNullOrEmpty()) {
            tvCate.visibility = View.VISIBLE
            tvCate.text = "Genre: ${GenreHelper.getGenres(movie.genres)}"
        } else tvCate.visibility = View.GONE
        if (movie.runtime != null) {
            tvRunTime.visibility = View.VISIBLE
            tvRunTime.text = "Time: ${Utils.getRunTime(movie.runtime!!)}"
        } else tvRunTime.visibility = View.GONE

        if (!movie.tagline.isNullOrEmpty()) {
            tvTagLine.visibility = View.VISIBLE
            tvTagLine.text = "Tagline: ${movie.tagline}"
        } else tvTagLine.visibility = View.GONE
        rbRating.rating = movie.voteAverage?.toFloat() ?: 0f
        tvScore.text = movie.voteAverage?.toString()
        tvDescStory.text = movie.overview
    }

    override fun onFailure() {

    }

    private fun initClickEvent() {
        ivPlay.setOnClickListener {
            playVideo()
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun playVideo() {
        if (idVideo != null) {
            val fm = fragmentManager
            val tag = YouTubePlayerFragment::class.java.simpleName
            playerFragment = fm.findFragmentByTag(tag) as YouTubePlayerFragment?
            if (playerFragment == null) {
                val ft = fm.beginTransaction()
                playerFragment = YouTubePlayerFragment.newInstance()
                ft.add(android.R.id.content, playerFragment, tag)
                ft.commit()
            }
            playerFragment?.initialize(BuildConfig.API_KEY, object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer,
                    b: Boolean
                ) {
                    youtubePlayer = youTubePlayer
                    youTubePlayer.loadVideo(idVideo)
                    youTubePlayer.setOnFullscreenListener {
                        isFullScreen = it
                    }
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    showMessage("Failed to load video!")
                }
            })
        } else showMessage("The trailer is not available!")
    }

    override fun onBackPressed() {
        if (isFullScreen && youtubePlayer != null)
            youtubePlayer?.setFullscreen(false)
        else if (playerFragment?.isVisible == true)
            fragmentManager.beginTransaction().remove(playerFragment).commit()
        else
            super.onBackPressed()
    }

    private fun initRecyclerView() {
    }

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}