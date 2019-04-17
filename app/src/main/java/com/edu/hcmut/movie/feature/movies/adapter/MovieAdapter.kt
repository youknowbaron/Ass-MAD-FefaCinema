package com.edu.hcmut.movie.feature.movies.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.helper.GenreHelper
import com.edu.hcmut.movie.helper.ImageHelper
import com.edu.hcmut.movie.model.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MovieAdapter(private val context: Context?) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(movie: Movie?) {
            tvTitle.text = movie?.title
            tvVoteAverage.text = movie?.voteAverage.toString()
            val genres = GenreHelper.getGenresFromIds(movie?.genreIds)
            tvGenres.text = genres
            tvReleaseDate.text = movie?.releaseDate
            val linkImage = ImageHelper.getLinkImage(movie?.posterPath, ImageHelper.NORMAL_QUALITY)
            Glide.with(context ?: return)
                .load(linkImage)
                .into(imvPoster )
        }
    }
}