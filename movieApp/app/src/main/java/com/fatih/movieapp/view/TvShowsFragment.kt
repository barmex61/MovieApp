package com.fatih.movieapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatih.movieapp.R
import com.fatih.movieapp.adapter.MostPopularTvShowAdapter
import com.fatih.movieapp.databinding.FragmentTvShowsBinding
import com.fatih.movieapp.listener.TvShowListener
import com.fatih.movieapp.model.TvShow
import com.fatih.movieapp.viewmodel.TvShowFragmentViewModel



class TvShowsFragment : Fragment(),TvShowListener {

    private lateinit var binding: FragmentTvShowsBinding
    private lateinit var viewModel: TvShowFragmentViewModel
    private lateinit var adapter: MostPopularTvShowAdapter
    private var currentPage=1
    private var totalAvailablePages=1
    private var tvShows=ArrayList<TvShow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_tv_shows,container,false)
        doInitialization()
        observeLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun doInitialization(){
        viewModel= ViewModelProvider(this)[TvShowFragmentViewModel::class.java]
        adapter= MostPopularTvShowAdapter(tvShows,this)
        binding.mainActivityRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.mainActivityRecyclerView.setHasFixedSize(true)
        binding.mainActivityRecyclerView.adapter=adapter
        binding.mainActivityRecyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!binding.mainActivityRecyclerView.canScrollVertically(1)){
                    if (currentPage<=totalAvailablePages){
                        currentPage++
                        observeLiveData()
                    }
                }
            }
        } )
        binding.imageWatch.setOnClickListener {
            goWatchList(it)
        }
        binding.imageSearch.setOnClickListener {
            findNavController().navigate(TvShowsFragmentDirections.actionTvShowsFragmentToSearchFragment())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeLiveData(){
        binding.isLoading=true
        viewModel.getMostPopularTvShows(currentPage).observe(viewLifecycleOwner
        ) { tvShowResponse ->
            if (tvShowResponse != null) {
                totalAvailablePages = tvShowResponse.pages
                var oldSize = tvShows.size
                tvShows.addAll(tvShowResponse.tvShows)
                adapter.notifyItemRangeInserted(oldSize, tvShows.size)
                binding.isLoading=false
            }
        }
    }

    private fun goWatchList(view: View){
            findNavController().navigate(TvShowsFragmentDirections.actionTvShowsFragmentToWatchListFragment())
    }

    override fun onTvShowClicked(view:View, tvShow: TvShow) {
        val action=TvShowsFragmentDirections.actionTvShowsFragmentToTvShowDetailsFragment(tvShow)
        Navigation.findNavController(view).navigate(action)
    }
}