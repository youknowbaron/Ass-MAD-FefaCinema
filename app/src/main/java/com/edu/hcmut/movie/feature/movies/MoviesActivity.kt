package com.edu.hcmut.movie.feature.movies

import android.databinding.DataBindingUtil.setContentView
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.edu.hcmut.movie.R
import com.edu.hcmut.movie.R.id.tabLayout
import com.edu.hcmut.movie.R.id.viewPager
import com.edu.hcmut.movie.feature.movies.adapter.MoviesPagerAdapter
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var lastPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        initView()
    }

    private fun initView() {
        val pagerAdapter = MoviesPagerAdapter(supportFragmentManager, this)
        viewPager.offscreenPageLimit = MoviesPagerAdapter.NUM_PAGE
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.customView = pagerAdapter.createTabView(0, true)
        tabLayout.getTabAt(1)?.customView = pagerAdapter.createTabView(1)
        tabLayout.getTabAt(2)?.customView = pagerAdapter.createTabView(2)
        viewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(position: Int) {
        tabLayout.getTabAt(position)?.customView?.findViewById<TextView>(R.id.tvTabTitle)?.apply {
            setTextColor(ContextCompat.getColor(this@MoviesActivity, R.color.tab_selected))
            typeface = Typeface.DEFAULT_BOLD
            textSize = 20f
        }
        tabLayout.getTabAt(lastPage)?.customView?.findViewById<TextView>(R.id.tvTabTitle)?.apply {
            setTextColor(ContextCompat.getColor(this@MoviesActivity, R.color.tab_unselected))
            typeface = Typeface.DEFAULT
            textSize = 14f
        }
        lastPage = position
    }
}