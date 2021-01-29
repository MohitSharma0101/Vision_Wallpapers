package com.vision.wallpapers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.vision.wallpapers.databinding.ActivityFullImageBinding

class FullImageActivity : AppCompatActivity() {

   lateinit var binding:ActivityFullImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
        binding.wallpaperIv.load(url)
//        Glide.with(this).load(url).centerCrop().into(binding.wallpaperIv)
    }
}