package com.edu.hcmut.movie.feature.movies.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.feature.movies.now.NowPlayingFragment
import com.edu.hcmut.movie.feature.movies.popular.PopularFragment
import com.edu.hcmut.movie.feature.movies.upcoming.UpcomingFragment

class MoviesPagerAdapter(fm: FragmentManager, private val context: Context?) : FragmentPagerAdapter(fm) {

    companion object {
        const val NUM_PAGE = 3
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> NowPlayingFragment.newInstance()
        1 -> UpcomingFragment.newInstance()
        2 -> PopularFragment.newInstance()
        else -> NowPlayingFragment.newInstance()
    }

    fun createTabView(position: Int, isSelected: Boolean = false): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_custom, null)
        view.findViewById<TextView>(R.id.tvTabTitle).apply {
            if (isSelected) {
                setTextColor(ContextCompat.getColor(context, R.color.tab_selected))
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
            }
            text = when (position) {
                0 -> context.getString(R.string.now_playing)
                1 -> context.getString(R.string.upcoming)
                2 -> context.getString(R.string.popular)
                else -> context.getString(R.string.now_playing)
            }
        }
        return view
    }

    override fun getCount(): Int = NUM_PAGE
}