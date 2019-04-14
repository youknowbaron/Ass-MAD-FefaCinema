package com.edu.hcmut.movie.feature.movies.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.edu.hcmut.movie.feature.movies.now.NowPlayingFragment
import com.edu.hcmut.movie.feature.movies.popular.PopularFragment
import com.edu.hcmut.movie.feature.movies.upcoming.UpcomingFragment

class MoviesPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        const val NUM_PAGE = 3
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> NowPlayingFragment.newInstance()
        1 -> UpcomingFragment.newInstance()
        2 -> PopularFragment.newInstance()
        else -> NowPlayingFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "Đang chiếu"
        1 -> "Sắp chiếu"
        2 -> "Phim hay"
        else -> "Đang chiếu"
    }

    override fun getCount(): Int = NUM_PAGE
}