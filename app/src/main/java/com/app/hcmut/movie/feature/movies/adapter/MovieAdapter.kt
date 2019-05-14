package com.app.hcmut.movie.feature.movies.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hcmut.movie.R
import com.app.hcmut.movie.ext.showMessage
import com.app.hcmut.movie.helper.GenreHelper
import com.app.hcmut.movie.helper.ImageHelper
import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.util.NORMAL_QUALITY
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*
import java.util.concurrent.atomic.AtomicInteger

class MovieAdapter(private val context: Context?, private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies: MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        Log.d("createViewHolder", "${countInflate.incrementAndGet()}")
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            movies[position].id?.let { it1 -> listener.invoke(it1) }
        }
    }

    fun setData(movies: MutableList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun addAll(movies:List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearAll() {
        this.movies.clear()
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(movie: Movie?) {
            tvTitle.text = movie?.title
            tvVoteAverage.text = movie?.voteAverage.toString()
            val genres = GenreHelper.getGenresFromIds(movie?.genreIds)
            tvGenres.text = genres
            tvReleaseDate.text = movie?.releaseDate
            val linkImage = ImageHelper.getLinkImage(movie?.posterPath, NORMAL_QUALITY)
            Glide.with(context?.applicationContext ?: return)
                .load(linkImage)
                .into(imvPoster)
            cvBookTicket.setOnClickListener {
                context.showMessage("The cinema is under maintenance. We will come back soon.", "Sorry!")
            }
        }
    }

    companion object {
        private val countInflate: AtomicInteger = AtomicInteger(0)
    }
}