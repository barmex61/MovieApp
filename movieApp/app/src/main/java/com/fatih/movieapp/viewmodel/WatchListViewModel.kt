package com.fatih.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatih.movieapp.model.TvShow
import com.fatih.movieapp.room.TvShowDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListViewModel(application: Application) : AndroidViewModel(application) {
    private var watchList= MutableLiveData<List<TvShow>>()
    private var isLoading= MutableLiveData<Boolean>()
    private var tvShowDao= TvShowDatabase.invoke(application).tvShowDao()
    fun deleteTvShowAtWatchList(tvShow: TvShow){
        viewModelScope.launch(Dispatchers.Default){
            tvShowDao.delete(tvShow)
        }
    }
    fun getAllTvShowFromDatabase():LiveData<List<TvShow>>{
        isLoading.value=true
        viewModelScope.launch(Dispatchers.Default){
            val list=async {
                tvShowDao.getAll()

            }
           withContext(Dispatchers.Main){
               watchList.value=list.await()
               isLoading.value=false
               println(list.await().size)
           }
        }

        return watchList
    }
}