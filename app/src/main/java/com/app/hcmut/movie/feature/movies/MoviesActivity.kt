package com.app.hcmut.movie.feature.movies

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.app.hcmut.movie.R
import com.app.hcmut.movie.ext.toast
import com.app.hcmut.movie.feature.movies.adapter.MoviesPagerAdapter
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import kotlinx.android.synthetic.main.drawer_menu.*

class MoviesActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var lastPage = 0
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        initView()
        initToolbar()
        initDrawerMenu()
    }


    private fun initToolbar() {
        icDrawer.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

    }

    private fun initDrawerMenu() {
        tvSavedMovie.setOnClickListener {
            toast("saved movie")
        }
    }

    override fun onStart() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        googleApiClient.connect()
        super.onStart()
        super.onStart()
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

    override fun onBackPressed() {
        signOut()
        super.onBackPressed()
    }

    private fun signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            Toast.makeText(applicationContext, "Logged Out", Toast.LENGTH_SHORT).show()
        }
    }
}