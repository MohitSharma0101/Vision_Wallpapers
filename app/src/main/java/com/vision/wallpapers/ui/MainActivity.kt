package com.vision.wallpapers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.WallpaperViewModelFactory
import com.vision.wallpapers.databinding.ActivityMainBinding
import com.vision.wallpapers.repository.WallpaperRepo

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: WallpaperViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomBar.setupWithNavController(navController)

        val repo = WallpaperRepo()
        val factory = WallpaperViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory).get(WallpaperViewModel::class.java)


    }
}