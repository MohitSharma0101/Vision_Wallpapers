package com.vision.wallpapers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import coil.transform.BlurTransformation
import com.bumptech.glide.Glide
import com.vision.wallpapers.databinding.ActivityFullImageBinding

class FullImageActivity : AppCompatActivity() {

   lateinit var binding:ActivityFullImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
        binding.wallpaperIv.load(url){
            crossfade(true)
            allowHardware(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }
}