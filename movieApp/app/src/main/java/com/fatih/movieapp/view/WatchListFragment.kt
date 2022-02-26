package com.fatih.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.movieapp.R
import com.fatih.movieapp.adapter.WatchListAdapter
import com.fatih.movieapp.databinding.FragmentWatchListBinding
import com.fatih.movieapp.listener.WatchListListener
import com.fatih.movieapp.model.TvShow
import com.fatih.movieapp.viewmodel.WatchListViewModel

class WatchListFragment : Fragment(),WatchListListener {
    private var watchList=ArrayList<TvShow>()
    private lateinit var binding:FragmentWatchListBinding
    private lateinit var adapter:WatchListAdapter
    private lateinit var viewModel:WatchListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_watch_list,container,false)
        doInitialization()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    private fun doInitialization(){
        viewModel= ViewModelProvider(this)[WatchListViewModel::class.java]
        adapter= WatchListAdapter(watchList,this)
        binding.watchListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.watchListRecyclerView.adapter=adapter
        binding.imageBack.setOnClickListener {
            goBack()
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.getAllTvShowFromDatabase().observe(viewLifecycleOwner){tvShowList ->
            if(tvShowList!=null){
                if(watchList.size>0){
                    watchList.clear()
                }
                watchList=ArrayList(tvShowList)
                adapter.updateList(watchList)
            }
        }
    }
    private fun goBack(){
        findNavController().popBackStack()
    }
    override fun deleteTvShow(position:Int, tvShow: TvShow) {
        viewModel.deleteTvShowAtWatchList(tvShow)
        watchList.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position,watchList.size)
    }

    override fun onTvShowClicked(tvShow: TvShow) {
        findNavController().navigate(WatchListFragmentDirections.actionWatchListFragmentToTvShowDetailsFragment(tvShow))
    }
}