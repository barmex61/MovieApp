package com.fatih.movieapp.service

import com.fatih.movieapp.model.TvShowDetails
import com.fatih.movieapp.model.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowApi {
    //ae624ef782f69d5092464dffa234178b
    //https://www.episodate.com/api/most-popular?page=1
    @GET("most-popular")
    fun getMostPopularTvShows(@Query("page") page:Int):Call<TvShowResponse>
    @GET("show-details")
    fun getTvShowDetails(@Query("q") name:String):Call<TvShowDetails>
    @GET("search")
    fun searchTvShows(@Query("q")name: String,@Query("page")page: Int):Call<TvShowResponse>
}