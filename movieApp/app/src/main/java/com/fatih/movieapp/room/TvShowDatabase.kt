package com.fatih.movieapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fatih.movieapp.model.TvShow

@Database(entities = [TvShow::class], version = 1)
abstract class TvShowDatabase: RoomDatabase() {
    abstract fun tvShowDao():TvShowDao

    companion object{
        @Volatile private var tvShowDatabase:TvShowDatabase?=null

        operator fun invoke(context: Context)= tvShowDatabase?: synchronized(Any()){
            tvShowDatabase?:createTvShowDataBase(context).also {
                tvShowDatabase=it
            }
        }
        private fun createTvShowDataBase(context:Context):TvShowDatabase{
            return Room.databaseBuilder(context.applicationContext,TvShowDatabase::class.java,"TvShowWatchList").build()
        }
    }
}