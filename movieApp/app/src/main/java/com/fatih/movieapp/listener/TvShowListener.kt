package com.fatih.movieapp.listener

import android.view.View
import com.fatih.movieapp.model.TvShow

interface TvShowListener {
    fun onTvShowClicked(view: View, tvShow:TvShow)
}