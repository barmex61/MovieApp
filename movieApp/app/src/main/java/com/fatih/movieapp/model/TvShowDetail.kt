package com.fatih.movieapp.model


import com.google.gson.annotations.SerializedName

data class TvShowDetail(
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: String,
    @SerializedName("pictures")
    val pictures: List<String>,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("rating_count")
    val ratingCount: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("url")
    val url: String
)

