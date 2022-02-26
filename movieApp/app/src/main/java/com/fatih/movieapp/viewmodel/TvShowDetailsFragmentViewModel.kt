package com.fatih.movieapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.fatih.movieapp.model.TvShow
import com.fatih.movieapp.model.TvShowDetails
import com.fatih.movieapp.room.TvShowDatabase
import com.fatih.movieapp.service.TvShowService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDetailsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var tvShowDetails=MutableLiveData<TvShowDetails>()
    private val tvShowService=TvShowService.invoke()
    private val tvShowDao=TvShowDatabase.invoke(application).tvShowDao()
    private var selectedTvShow=MutableLiveData<TvShow?>()
    private var isItIN:Boolean?=null
    fun getTvShowDetailsFromApi(name:String):LiveData<TvShowDetails>{
        tvShowService.getTvShowDetails(name).enqueue(object :Callback<TvShowDetails>{
            override fun onResponse(call: Call<TvShowDetails>, response: Response<TvShowDetails>) {
                tvShowDetails.value=response.body()
            }

            override fun onFailure(call: Call<TvShowDetails>, t: Throwable) {
                t.localizedMessage
            }

        })
        return tvShowDetails
    }
    fun addTvShowToDatabase(tvShow:TvShow){
        viewModelScope.launch(Dispatchers.Default){
            tvShowDao.insert(tvShow)
        }
        Toast.makeText(getApplication(),"Tv show added into database",Toast.LENGTH_SHORT).show()
    }
    fun isItInDatabase(id:Int):LiveData<TvShow?>{

        viewModelScope.launch(Dispatchers.Default){
           val tvShow=tvShowDao.getSelected(id)
            withContext(Dispatchers.Main){
                selectedTvShow.value=tvShow
            }
        }
        return selectedTvShow
    }
    fun deleteTvShowFromDatabase(tvShow: TvShow){
        viewModelScope.launch(Dispatchers.Default){
            tvShowDao.delete(tvShow)
        }
        Toast.makeText(getApplication(),"Tv show deleted from database",Toast.LENGTH_SHORT).show()
    }
}