package com.fatih.movieapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatih.movieapp.model.TvShowResponse
import com.fatih.movieapp.service.TvShowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel(application: Application) : AndroidViewModel(application) {
        private val tvShowService=TvShowService.invoke()
        private val tvShowResponse=MutableLiveData<TvShowResponse>()

        fun searchTvShow(name:String,page:Int):LiveData<TvShowResponse>{
            tvShowService.searchTvShows(name,page).enqueue(object :Callback<TvShowResponse>{
                override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                    tvShowResponse.value=response.body()
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Toast.makeText(getApplication(),"Something went wrong",Toast.LENGTH_SHORT).show()
                }

            })
            return tvShowResponse
        }
}