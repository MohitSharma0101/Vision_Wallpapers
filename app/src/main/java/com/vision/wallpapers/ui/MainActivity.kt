package com.vision.wallpapers.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.WallpaperViewModelFactory
import com.vision.wallpapers.database.WallpaperDatabase
import com.vision.wallpapers.databinding.ActivityMainBinding
import com.vision.wallpapers.databinding.BottomSheetBinding
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

        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val byViews = findViewById<TextView>(R.id.byViews)
            byViews.setOnClickListener {
                viewModel.getAlphaPhotos("by_views")
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        bottomSheet.setOnClickListener {

        }

        val db = WallpaperDatabase(this)
        val repo = WallpaperRepo(db)
        val factory = WallpaperViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory).get(WallpaperViewModel::class.java)


    }


    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }

    }
    }