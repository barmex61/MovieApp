package com.fatih.movieapp.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity
data class TvShow(
    @ColumnInfo(name = "country")
    @SerializedName("country")
    val country: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "image_thumbnail_path")
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "network")
    @SerializedName("network")
    val network: String,
    @ColumnInfo(name = "startDate")
    @SerializedName("start_date")
    val startDate: String,
    @ColumnInfo(name = "status")
    @SerializedName("status")
    val status: String
):Serializable