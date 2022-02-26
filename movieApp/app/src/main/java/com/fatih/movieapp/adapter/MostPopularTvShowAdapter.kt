package com.fatih.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.movieapp.R
import com.fatih.movieapp.databinding.ActivitymainRowBinding
import com.fatih.movieapp.listener.TvShowListener
import com.fatih.movieapp.model.TvShow

class MostPopularTvShowAdapter(private var mostPopularTvShowList:ArrayList<TvShow>, private val tvShowListener: TvShowListener): RecyclerView.Adapter<MostPopularTvShowAdapter.MostPopularTvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularTvShowViewHolder {
        val binding=DataBindingUtil.inflate<ActivitymainRowBinding>(LayoutInflater.from(parent.context),
                R.layout.activitymain_row,parent,false)
        return MostPopularTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MostPopularTvShowViewHolder, position: Int) {
            holder.binding.tvShows=mostPopularTvShowList[position]
            holder.binding.deleteImage.visibility=View.GONE
            holder.itemView.setOnClickListener {
                tvShowListener.onTvShowClicked(it,mostPopularTvShowList[position])
            }
    }

    override fun getItemCount(): Int {
        return mostPopularTvShowList.size
    }
    class MostPopularTvShowViewHolder(val binding:ActivitymainRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateList(list:List<TvShow>){
        mostPopularTvShowList.clear()
        mostPopularTvShowList.addAll(list)
    }
}