package com.fatih.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.movieapp.R
import com.fatih.movieapp.databinding.ActivitymainRowBinding
import com.fatih.movieapp.listener.WatchListListener
import com.fatih.movieapp.model.TvShow

class WatchListAdapter(private var watchList:ArrayList<TvShow>, private val listener:WatchListListener): RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val binding=DataBindingUtil.inflate<ActivitymainRowBinding>(LayoutInflater.from(parent.context),
            R.layout.activitymain_row,parent,false)
        return WatchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.binding.tvShows=watchList[position]
        println("merhaba")
        holder.binding.deleteImage.setOnClickListener {
            listener.deleteTvShow(position,watchList[position])
        }
        holder.itemView.setOnClickListener {
            listener.onTvShowClicked(watchList[position])
        }
    }

    override fun getItemCount(): Int {
        return watchList.size
    }
    class WatchListViewHolder(val binding:ActivitymainRowBinding) : RecyclerView.ViewHolder(binding.root)
    fun updateList(list:ArrayList<TvShow>){
        this.watchList.clear()
        this.watchList=list
        notifyDataSetChanged()
    }
}