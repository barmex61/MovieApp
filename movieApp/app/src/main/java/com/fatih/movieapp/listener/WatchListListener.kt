package com.fatih.movieapp.listener

import android.view.View
import com.fatih.movieapp.model.TvShow

interface WatchListListener {
    fun deleteTvShow(position:Int,tvShow:TvShow)
    fun onTvShowClicked(tvShow: TvShow)
}