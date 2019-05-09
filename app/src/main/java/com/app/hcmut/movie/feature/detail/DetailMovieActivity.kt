package com.app.hcmut.movie.feature.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import android.widget.Toast
import com.app.hcmut.movie.BuildConfig
import com.app.hcmut.movie.R
import com.app.hcmut.movie.helper.GenreHelper
import com.app.hcmut.movie.helper.ImageHelper
import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.model.Videos
import com.app.hcmut.movie.util.HIGH_QUALITY
import com.app.hcmut.movie.util.MOVIE_ID
import com.app.hcmut.movie.util.NORMAL_QUALITY
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

        presenter.getMovieDetail(movieId)
        presenter.getVideoTrailer(movieId)

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
        idVideo = video.results?.get(0)?.key
    }

    private fun setDataOnView(movie: Movie) {
        Glide.with(this)
            .load(ImageHelper.getLinkImage(movie.backdropPath, HIGH_QUALITY))
            .into(ivVideo)
        Glide.with(this)
            .load(ImageHelper.getLinkImage(movie.posterPath, NORMAL_QUALITY))
            .into(ivPoster)
        tvTitle.text = movie.title
        if (movie.genreIds != null) {
            tvCate.text = GenreHelper.getGenresFromIds(movie.genreIds)
        } else tvCate.visibility = View.GONE

        tvTagLine.text = movie.tagline
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
                    Toast.makeText(this@DetailMovieActivity, "Failed to load video!", Toast.LENGTH_SHORT).show()
                }
            })
        } else
            Toast.makeText(this, "This movie has no video trailer!", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (isFullScreen && youtubePlayer != null)
            youtubePlayer?.setFullscreen(false)
        else if (playerFragment?.isVisible == true)
            fragmentManager.beginTransaction().remove(playerFragment).commit()
        else
            super.onBackPressed()
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