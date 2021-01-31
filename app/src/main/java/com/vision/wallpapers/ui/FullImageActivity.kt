package com.vision.wallpapers.ui

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.vision.wallpapers.R
import com.vision.wallpapers.databinding.ActivityFullImageBinding


class FullImageActivity : AppCompatActivity() {

   lateinit var binding:ActivityFullImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
        binding.apply {
            wallpaperIv.load(url){
                crossfade(true)
                allowHardware(true)
            }
            scaleBtn.setOnClickListener {
               adjustZoom()
            }
            rotateBtn.setOnClickListener {
               changeScreenOrientation()
            }
        }
    }

    private fun changeScreenOrientation() {
        val orientation: Int = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        if (Settings.System.getInt(contentResolver,
                        Settings.System.ACCELEROMETER_ROTATION, 0) === 1) {
            val handler = Handler()
            handler.postDelayed(Runnable { requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR }, 4000)
        }
    }
    private fun adjustZoom(){
        binding.apply {
            if (wallpaperIv.scaleType == ImageView.ScaleType.CENTER_CROP) {
                scaleBtn.setImageResource(R.drawable.ic_maximize)
                wallpaperIv.scaleType = ImageView.ScaleType.FIT_CENTER
            } else {
                scaleBtn.setImageResource(R.drawable.ic_minimize)
                wallpaperIv.scaleType = ImageView.ScaleType.CENTER_CROP
                wallpaperIv.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}