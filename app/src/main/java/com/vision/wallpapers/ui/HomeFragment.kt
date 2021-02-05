package com.vision.wallpapers.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentHomeBinding
import com.vision.wallpapers.util.Constants
import com.vision.wallpapers.util.Resources
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter
    lateinit var viewModel: WallpaperViewModel
    var isLoading = false
    var isScrolling = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val activity = (activity as MainActivity)

        viewModel = activity.viewModel
        viewModel.getAlphaPhotos()
        gridLayoutManager = GridLayoutManager(context, 2)
        adapter = Adapter(viewModel)
        binding.homeRecyclerView.layoutManager = gridLayoutManager
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        binding.sort.setOnClickListener {
            activity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.retryBtn.setOnClickListener { 
            viewModel.getAlphaPhotos()
        }


        adapter.setOnItemClickListener { image, url, photo ->
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("photo", photo)
            val bundle = ActivityOptionsCompat.makeCustomAnimation(
                requireContext(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            ).toBundle()
            startActivity(intent, bundle)
        }

        adapter.setSaveOnClickListener {
            viewModel.saveWallpaper(it)
        }

        if(viewModel.method != Constants.HIGH_RATED){
           val s = viewModel.method
            if(s == Constants.NEWEST){
                binding.lChip.isChecked = true
            }else if(s == Constants.POPULAR){
                binding.pChip.isChecked = true
            }else{
                binding.fChip.isChecked = true
            }
        }
        handelChips()

        getAlphaImages()
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = gridLayoutManager.childCount
            val totalItemCount: Int = gridLayoutManager.itemCount
            val firstVisibleItemPosition: Int = gridLayoutManager.findFirstVisibleItemPosition()
            if(!isLoading && !viewModel.alphaLastPage){
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0 && totalItemCount >= 30 && isScrolling
                ) {
                    viewModel.getAlphaPhotos(page = viewModel.alphaPage)
                    isScrolling = false
                }
            }

        }
    }


    private fun getUnSplashPhotos() {
        viewModel.getUnsplashPhotos()
        viewModel.unsplashPhotos.observe(viewLifecycleOwner, Observer { photos ->
            when (photos) {
                is Resources.Success -> {
                    photos.data?.let {
                        adapter.differ.submitList(it.list)
                    }
                }
            }
        })
    }

    private fun getAlphaImages(){
        viewModel.alphaPhoto.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resources.Success -> {
                    isLoading = false
                    binding.progressBar.visibility = View.GONE
                    binding.loading.visibility = View.GONE
                    it.data?.let { list ->
                        viewModel.alphaLastPage = list.is_last ?: false
                        adapter.differ.submitList(list.wallpapers.toList())
                    }
                }
                is Resources.Loading -> {
                    isLoading = true
                    binding.progressBar.visibility = View.VISIBLE
                    binding.loading.visibility = View.VISIBLE
                }
                else -> {
                    binding.retryBtn.visibility = View.VISIBLE
                    binding.loading.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                }
            }
        })
    }


    private fun searchUnSplash(query: String) {
        viewModel.searchUnsplashPhotos(query)
        viewModel.unsplashSearchPhotos.observe(viewLifecycleOwner, Observer { searchResults ->
            when (searchResults) {
                is Resources.Success -> {
                    searchResults.data?.let {
                        adapter.differ.submitList(it.results)
                    }
                }
                is Resources.Loading -> {
                }
            }
        })
    }

    private fun handelChips() {
        binding.apply {
            chipGroup.setOnCheckedChangeListener { group, checkedId ->
               val chip = chipGroup.findViewById<Chip>(checkedId)
                chip?.let {
                    val s =  chip.text.toString().toLowerCase(Locale.ROOT)
                    viewModel.method = s
                    viewModel.getAlphaPhotos(s)
                }
            }
        }
    }
}