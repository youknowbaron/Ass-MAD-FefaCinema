package com.app.hcmut.movie.feature.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hcmut.movie.R
import com.app.hcmut.movie.helper.ImageHelper
import com.app.hcmut.movie.model.Movie
import com.app.hcmut.movie.util.NORMAL_QUALITY
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recommend.*

class DetailAdapter(private val context: Context?, private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    private val listMovie: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recommend, parent, false))
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovie[position])
        holder.itemView.setOnClickListener {
            listener.invoke(listMovie[position])
        }
    }

    fun setData(data: MutableList<Movie>) {
        listMovie.clear()
        listMovie.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(movie: Movie) {
            Glide.with(context?.applicationContext ?: return)
                .load(ImageHelper.getLinkImage(movie.posterPath, NORMAL_QUALITY))
                .into(ivPoster)
            tvTitle.text = movie.originalTitle
            tvVoteAverage.text = movie.voteAverage.toString()
        }
    }
}