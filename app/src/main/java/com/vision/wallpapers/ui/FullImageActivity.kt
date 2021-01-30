package com.vision.wallpapers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.api.load
import coil.transform.BlurTransformation
import com.bumptech.glide.Glide
import com.vision.wallpapers.R
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
        binding.apply {
            scaleBtn.setOnClickListener {
              if( wallpaperIv.scaleType == ImageView.ScaleType.CENTER_CROP){
                  scaleBtn.setImageResource(R.drawable.ic_maximize)
                  wallpaperIv.scaleType = ImageView.ScaleType.FIT_CENTER
              }else{
                  scaleBtn.setImageResource(R.drawable.ic_minimize)
                  wallpaperIv.scaleType = ImageView.ScaleType.CENTER_CROP
                  wallpaperIv.scaleType = ImageView.ScaleType.CENTER_CROP
              }
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

}