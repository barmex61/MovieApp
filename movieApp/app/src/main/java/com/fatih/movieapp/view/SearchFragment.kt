package com.fatih.movieapp.view

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fatih.movieapp.R
import com.fatih.movieapp.adapter.MostPopularTvShowAdapter
import com.fatih.movieapp.databinding.FragmentSearchBinding
import com.fatih.movieapp.listener.TvShowListener
import com.fatih.movieapp.model.TvShow
import com.fatih.movieapp.viewmodel.SearchFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class SearchFragment : Fragment(),TvShowListener {
    private lateinit var binding: FragmentSearchBinding
    private var viewModel: SearchFragmentViewModel? = null
    private var tvShowArrayList = ArrayList<TvShow>()
    private var tvShowAdapter: MostPopularTvShowAdapter? = null
    private var currentPage = 1
    private var oldCount=0
    private var totalAvailablePages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        doInitialization()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onTvShowClicked(view: View, tvShow: TvShow) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTvShowDetailsFragment(tvShow))
    }

    private fun doInitialization() {
        binding.searchButtonBack.setOnClickListener { findNavController().popBackStack() }
        binding.searchFragmentRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        tvShowAdapter = MostPopularTvShowAdapter(tvShowArrayList, this)
        binding.searchFragmentRecyclerView.adapter = tvShowAdapter
        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().trim { it <= ' ' }.isNotEmpty()) {
                  CoroutineScope(Dispatchers.Main).launch {

                      binding.isLoading=true
                      delay(300)
                      currentPage=1
                      totalAvailablePages=1
                      searchTvShow(editable.toString())
                  }
                } else {
                    tvShowArrayList.clear()
                    tvShowAdapter!!.notifyDataSetChanged()
                }
            }
        })
        binding.searchFragmentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.searchFragmentRecyclerView.canScrollVertically(1)) {
                    if (binding.searchText.text.toString().isNotEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            CoroutineScope(Dispatchers.Main).launch {
                                binding.isLoading=true
                                delay(300)
                                currentPage += 1
                                searchTvShow(binding.searchText.text.toString())

                            }
                        }
                    }
                }
            }
        })
        binding.searchText.requestFocus()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun searchTvShow(query: String) {

        viewModel!!.searchTvShow(query, currentPage).observe(viewLifecycleOwner) { tvShowResponses ->

                if (tvShowResponses != null) {
                    totalAvailablePages= tvShowResponses.pages
                    tvShowAdapter!!.updateList(tvShowResponses.tvShows)
                    tvShowAdapter!!.notifyDataSetChanged()
                    tvShowAdapter!!.notifyItemRangeInserted(oldCount, tvShowArrayList.size)
                    oldCount = tvShowResponses.tvShows.size
                    binding.isLoading=false

                }
            }
    }

}