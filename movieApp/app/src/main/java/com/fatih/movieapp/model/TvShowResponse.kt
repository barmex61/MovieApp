package com.fatih.movieapp.model


import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: String,
    @SerializedName("tv_shows")
    val tvShows: List<TvShow>
)



