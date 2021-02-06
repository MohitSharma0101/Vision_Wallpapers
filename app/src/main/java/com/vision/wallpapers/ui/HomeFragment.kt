package com.vision.wallpapers.ui

import am.appwise.components.ni.NoInternetDialog
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentHomeBinding
import com.vision.wallpapers.util.Constants
import com.vision.wallpapers.util.Resources
import java.io.IOException
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter
    lateinit var viewModel: WallpaperViewModel
    lateinit var noInternetDialog: NoInternetDialog
    var isLoading = false
    var isScrolling = false
    var mInterstitialAd: InterstitialAd? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val activity = (activity as MainActivity)

        viewModel = activity.viewModel
        noInternetDialog = activity.noInternetDialog

        gridLayoutManager = GridLayoutManager(context, 2)
        adapter = Adapter(viewModel)
        binding.homeRecyclerView.layoutManager = gridLayoutManager
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        binding.sort.setOnClickListener {
            activity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.retryBtn.setOnClickListener {
            binding.retryBtn.visibility = View.GONE
            viewModel.getAlphaPhotos()
        }

        loadInterstitial(activity)

        adapter.setOnItemClickListener { image, url, photo ->
            if (viewModel.counter == 5) {
                viewModel.counter = 0
                loadInterstitial(activity)
                Log.d("Add", "entered${mInterstitialAd.toString()}")
            }
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("photo", photo)
            val bundle = ActivityOptionsCompat.makeCustomAnimation(
                requireContext(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            ).toBundle()
            startActivity(intent, bundle)
            viewModel.counter++
            Log.d("Add", viewModel.counter.toString())
        }
        adapter.setSaveOnClickListener {
            viewModel.saveWallpaper(it)
        }

        if(viewModel.method != Constants.HIGH_RATED){
           val s = viewModel.method
            if(s == Constants.NEWEST){
                binding.lChip.isChecked = true
            }else if (s == Constants.POPULAR) {
                binding.pChip.isChecked = true
            } else {
                binding.fChip.isChecked = true
            }
        }
        handelChips()

        getAlphaImages()


    }

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }

    private fun loadInterstitial(activity: Activity) {

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })

        mInterstitialAd?.show(activity)

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = gridLayoutManager.childCount
                val totalItemCount: Int = gridLayoutManager.itemCount
                val firstVisibleItemPosition: Int = gridLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !viewModel.alphaLastPage) {
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
                    binding.retryBtn.visibility = View.GONE
                    it.data?.let { list ->
                        viewModel.alphaLastPage = list.is_last ?: false
                        adapter.differ.submitList(list.wallpapers.toList())
                    }
                }
                is Resources.Loading -> {
                    if (!isConnected()) {
                        binding.loading.visibility = View.VISIBLE
                        binding.retryBtn.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.loading.visibility = View.VISIBLE
                        isLoading = true
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
                is Resources.Error -> {
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
                    viewModel.alphaPhotoResponse = null
                    viewModel.getAlphaPhotos(s)
                }
            }
        }
    }
}