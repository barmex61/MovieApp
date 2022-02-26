package com.fatih.movieapp.model


import com.google.gson.annotations.SerializedName

data class TvShowDetails(
    @SerializedName("tvShow")
    val tvShow: TvShowDetail
)