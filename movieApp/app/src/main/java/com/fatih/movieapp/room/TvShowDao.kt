package com.fatih.movieapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fatih.movieapp.model.TvShow

@Dao
interface TvShowDao {
    @Insert
    suspend fun insert(tvShow:TvShow)
    @Delete
    suspend fun delete(tvShow: TvShow)
    @Query("SELECT * FROM TvShow")
    suspend fun getAll():List<TvShow>
    @Query("SELECT * FROM TvShow WHERE id=:inputId")
    suspend fun getSelected(inputId:Int):TvShow?
}