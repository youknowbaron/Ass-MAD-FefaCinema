package com.edu.hcmut.movie.feature.movies.upcoming

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.hcmut.movie.R

class UpcomingFragment : Fragment() {

    companion object {
        fun newInstance(): UpcomingFragment {
            return UpcomingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, null)
    }
}