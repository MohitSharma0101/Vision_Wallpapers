package com.vision.wallpapers.repository

import com.vision.wallpapers.api.RetrofitInstance

class WallpaperRepo {

    suspend fun getImages(
        perPage:Int = 15,
        pageNo:Int = 1) = RetrofitInstance.api.getImages(perPage,pageNo)
}