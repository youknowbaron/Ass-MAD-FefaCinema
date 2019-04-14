package com.edu.hcmut.movie.feature.movies

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.feature.movies.adapter.MoviesPagerAdapter
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        initView()
    }

    private fun initView() {
        val pagerAdapter = MoviesPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = MoviesPagerAdapter.NUM_PAGE
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(p0: Int) {}
}