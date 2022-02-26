package com.fatih.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatih.movieapp.model.TvShowResponse
import com.fatih.movieapp.service.TvShowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TvShowFragmentViewModel : ViewModel() {
    var tvShowResponse=MutableLiveData<TvShowResponse>()
    private val tvShowService=TvShowService.invoke()

    fun getMostPopularTvShows(page:Int):LiveData<TvShowResponse>{

            tvShowService.getMostPopularTvShows(page).enqueue(object :Callback<TvShowResponse>{
                override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                    tvShowResponse.value=response.body()
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    t.localizedMessage
                }
            })
        return tvShowResponse
        }
    }

