package com.fatih.movieapp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class TvShowService {


    companion object{
        @Volatile private var retrofit:TvShowApi?=null

        operator fun invoke()= retrofit?: synchronized(Any()){
            retrofit?:createRetrofit().also {
                retrofit=it
            }
        }
        private var client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        private fun createRetrofit():TvShowApi{
            return Retrofit.Builder().baseUrl("https://www.episodate.com/api/").client(client).addConverterFactory(GsonConverterFactory.create())
                .build().create(TvShowApi::class.java);
        }
    }
}