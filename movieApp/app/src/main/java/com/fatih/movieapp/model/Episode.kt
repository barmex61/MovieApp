package com.fatih.movieapp.model


import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("season")
    val season: Int
)