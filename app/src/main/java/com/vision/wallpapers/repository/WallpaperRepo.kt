package com.vision.wallpapers.repository

import com.vision.wallpapers.api.RetrofitInstance

class WallpaperRepo {

    suspend fun getImages() = RetrofitInstance.api.getImages()
}