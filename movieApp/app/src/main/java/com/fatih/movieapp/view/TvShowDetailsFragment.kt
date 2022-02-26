package com.fatih.movieapp.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.fatih.movieapp.R
import com.fatih.movieapp.adapter.EpisodesAdapter
import com.fatih.movieapp.adapter.TvShowDetailsAdapter
import com.fatih.movieapp.databinding.EpisodesBottomSheetDialogBinding
import com.fatih.movieapp.databinding.FragmentTvShowDetailsBinding
import com.fatih.movieapp.model.TvShow
import com.fatih.movieapp.model.TvShowDetails
import com.fatih.movieapp.viewmodel.TvShowDetailsFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TvShowDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTvShowDetailsBinding
    private lateinit var viewModel:TvShowDetailsFragmentViewModel
    private var selectedTvShow:TvShow?=null
    private lateinit var selectedTvShowName:String
    private var episodesBottomSheetDialog:BottomSheetDialog?=null
    private var isItIn:Boolean?=null
    private lateinit var layoutEpisodesBottomSheetBinding:EpisodesBottomSheetDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_tv_show_details,container,false)
        doInitialization()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun doInitialization(){
        arguments?.let {
            selectedTvShow=TvShowDetailsFragmentArgs.fromBundle(it).tvShow
            selectedTvShow?.let { tvShow ->
                selectedTvShowName= tvShow.id.toString()
            }
        }
        binding.imageBack.setOnClickListener { goBack() }
        binding.selectedTvShow=selectedTvShow
        binding.isLoading=true
        viewModel= ViewModelProvider(this)[TvShowDetailsFragmentViewModel::class.java]
        observeLiveData()
        binding.readMore.setOnClickListener {
            readMore()
        }
        binding.buttonWebSite.setOnClickListener { goWeb() }
        binding.buttonEpisodes.setOnClickListener { goEpisodes(it) }
        binding.imageWatchList.setOnClickListener {
            selectedTvShow?.let {
                watchList(it)
            }
        }
        isItInWatchList()

    }
    private fun observeLiveData(){
        viewModel.getTvShowDetailsFromApi(selectedTvShowName).observe(viewLifecycleOwner){tvShowDetails->
            binding.isLoading=false
            if(tvShowDetails!=null){
                binding.tvShowDetail=tvShowDetails.tvShow
                binding.description=tvShowDetails.tvShow.description.replace("\\<.*?\\>".toRegex(), "")
                loadImageSlider(ArrayList(tvShowDetails.tvShow.pictures))
                getRating(tvShowDetails)
                setBackgroundPic(ArrayList(tvShowDetails.tvShow.pictures))
            }
        }
    }
    private fun loadImageSlider(imageList:ArrayList<String>){
        binding.viewPager.offscreenPageLimit=1
        binding.viewPager.adapter=TvShowDetailsAdapter(imageList)
        setupSlider(imageList.size)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }

        })
    }

    private fun setupSlider(count: Int) {
        val indicator = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in 0 until count) {
            indicator[i] = ImageView(requireContext())
            indicator[i]!!.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.background_indicator_inactive
                )
            )
            indicator[i]!!.layoutParams = layoutParams
            binding.detailsFragmentLinearLayout.addView(indicator[i])
        }
        setCurrentIndicator(0)
    }
    private fun setCurrentIndicator(position:Int){
        val childCount=binding.detailsFragmentLinearLayout.childCount
        for(i in 0 until childCount){
            val imageView=binding.detailsFragmentLinearLayout.getChildAt(i) as ImageView
            if(i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.background_indicator_active))
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.background_indicator_inactive))
            }
        }
    }
    private fun goBack(){
        findNavController().popBackStack()
      //  findNavController().navigate(TvShowDetailsFragmentDirections.actionTvShowDetailsFragmentToTvShowsFragment())
    }
    private fun readMore(){
        if(binding.readMore.text.equals("Read More")){
            binding.textDescription.maxLines= Int.MAX_VALUE
            binding.textDescription.ellipsize=null
            binding.readMore.text="Read Less"
        }else{
            binding.textDescription.maxLines=4
            binding.textDescription.ellipsize=TextUtils.TruncateAt.END
            binding.readMore.text="Read More"
        }
    }
    private fun goWeb(){
            val intent=Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(binding.tvShowDetail?.url)
            startActivity(intent)
    }

    private fun goEpisodes(view:View){
        if(episodesBottomSheetDialog==null){
            episodesBottomSheetDialog= BottomSheetDialog(requireContext())
            layoutEpisodesBottomSheetBinding=DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.episodes_bottom_sheet_dialog,view.findViewById(R.id.episodesContainer),false)
            episodesBottomSheetDialog?.setContentView(layoutEpisodesBottomSheetBinding.root)
            episodesBottomSheetDialog?.dismissWithAnimation=true
            layoutEpisodesBottomSheetBinding.recyclerViewBottomSheet.adapter=EpisodesAdapter(ArrayList(binding.tvShowDetail!!.episodes))
            layoutEpisodesBottomSheetBinding.episodesTittle.text="Episodes | ${selectedTvShow?.name}"
            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener { episodesBottomSheetDialog?.dismiss() }
        }
        episodesBottomSheetDialog?.show()
    }
    private fun watchList(selectedTvShow:TvShow){
        if(isItIn==true){
            viewModel.deleteTvShowFromDatabase(selectedTvShow)
            binding.imageWatchList.setImageResource(R.drawable.ic_watch)
            isItIn=false
        }else{
            viewModel.addTvShowToDatabase(selectedTvShow)
            binding.imageWatchList.setImageResource(R.drawable.ic_added)
            isItIn=true
        }


    }
    private fun isItInWatchList(){
        selectedTvShow?.let { viewModel.isItInDatabase(it.id).observe(viewLifecycleOwner){it->
            isItIn = it != null
            println(isItIn)
            if(isItIn==true){
                binding.imageWatchList.setImageResource(R.drawable.ic_added)
            }else{
                binding.imageWatchList.setImageResource(R.drawable.ic_watch)
            }
        } }
    }
    private fun getRating(tvShowDetails: TvShowDetails){
        if(tvShowDetails.tvShow.rating.isNotEmpty()){
            var length=tvShowDetails.tvShow.rating.length
            if(length>3){
                binding.rating=tvShowDetails.tvShow.rating.substring(0,4)
            }else{
                binding.rating=tvShowDetails.tvShow.rating.substring(0,length)
            }
        }else{
            binding.rating="?"
        }

    }
    private fun setBackgroundPic(images:ArrayList<String>){
        var i=0
        CoroutineScope(Dispatchers.Default).launch {
                while (i<=images.size){
                    binding.imageUrl=images[i]
                    delay(3000)
                    i++
                    if(i==images.size){
                        i=0
                    }
                }

        }
    }

}
