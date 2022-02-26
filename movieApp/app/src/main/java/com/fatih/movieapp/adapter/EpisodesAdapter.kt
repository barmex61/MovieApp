package com.fatih.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.movieapp.R
import com.fatih.movieapp.databinding.EpisodesBottomSheetRowBinding
import com.fatih.movieapp.model.Episode

class EpisodesAdapter(private val episodesList:ArrayList<Episode>): RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding=DataBindingUtil.inflate<EpisodesBottomSheetRowBinding>(LayoutInflater.from(parent.context), R.layout.episodes_bottom_sheet_row,parent,false)
        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.binding.episodes=episodesList[position]
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }
    class EpisodesViewHolder(val binding:EpisodesBottomSheetRowBinding) : RecyclerView.ViewHolder(binding.root)

}