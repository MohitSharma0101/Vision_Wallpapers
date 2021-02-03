package com.vision.wallpapers.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textview.MaterialTextView
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.WallpaperViewModelFactory
import com.vision.wallpapers.database.WallpaperDatabase
import com.vision.wallpapers.databinding.ActivityMainBinding
import com.vision.wallpapers.repository.WallpaperRepo

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: WallpaperViewModel
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomBar.setupWithNavController(navController)

        val db = WallpaperDatabase(this)
        val repo = WallpaperRepo(db)
        val factory = WallpaperViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory).get(WallpaperViewModel::class.java)

        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)


        val byViews = findViewById<MaterialTextView>(R.id.byViews)
        val byFav = findViewById<MaterialTextView>(R.id.byFavorites)
        val byRating = findViewById<MaterialTextView>(R.id.byRating)
        val byPopularity = findViewById<MaterialTextView>(R.id.byPopularity)


        byViews.setOnClickListener {
            viewModel.alphaPhotoResponse = null
            viewModel.getAlphaPhotos("by_views")
            byViews.background = resources.getDrawable(R.drawable.bottom_back)
            byViews.setTextColor(resources.getColor(R.color.blue_link))

            byFav.background = null
            byFav.setTextColor(Color.BLACK)

            byRating.background = null
            byRating.setTextColor(Color.BLACK)

            byPopularity.background = null
            byPopularity.setTextColor(Color.BLACK)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        byFav.setOnClickListener {
            viewModel.alphaPhotoResponse = null
            viewModel.getAlphaPhotos("by_favorites")
            byFav.background = resources.getDrawable(R.drawable.bottom_back)
            byFav.setTextColor(resources.getColor(R.color.blue_link))

            byViews.background = null
            byViews.setTextColor(Color.BLACK)

            byRating.background = null
            byRating.setTextColor(Color.BLACK)

            byPopularity.background = null
            byPopularity.setTextColor(Color.BLACK)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        byRating.setOnClickListener {
            viewModel.alphaPhotoResponse = null
            viewModel.getAlphaPhotos("highest_rated")
            byRating.background = resources.getDrawable(R.drawable.bottom_back)
            byRating.setTextColor(resources.getColor(R.color.blue_link))

            byFav.background = null
            byFav.setTextColor(Color.BLACK)

            byViews.background = null
            byViews.setTextColor(Color.BLACK)

            byPopularity.background = null
            byPopularity.setTextColor(Color.BLACK)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        byPopularity.setOnClickListener {
            viewModel.alphaPhotoResponse = null
            viewModel.getAlphaPhotos("popular")
            byPopularity.background = resources.getDrawable(R.drawable.bottom_back)
            byPopularity.setTextColor(resources.getColor(R.color.blue_link))

            byFav.background = null
            byFav.setTextColor(Color.BLACK)

            byViews.background = null
            byViews.setTextColor(Color.BLACK)

            byRating.background = null
            byRating.setTextColor(Color.BLACK)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }


    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }

    }
    }