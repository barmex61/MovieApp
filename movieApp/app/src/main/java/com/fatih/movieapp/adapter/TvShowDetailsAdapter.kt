package com.fatih.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.movieapp.R
import com.fatih.movieapp.databinding.ViewPagerSingleItemBinding

class TvShowDetailsAdapter(private val imageList:ArrayList<String>): RecyclerView.Adapter<TvShowDetailsAdapter.TvShowDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowDetailsViewHolder {
        val binding=DataBindingUtil.inflate<ViewPagerSingleItemBinding>(LayoutInflater.from(parent.context),
            R.layout.view_pager_single_item,parent,false)
        return TvShowDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowDetailsViewHolder, position: Int) {
        holder.binding.imageUrl=imageList[position]
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
    class TvShowDetailsViewHolder(val binding:ViewPagerSingleItemBinding) : RecyclerView.ViewHolder(binding.root)

}