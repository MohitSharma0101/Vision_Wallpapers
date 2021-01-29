package com.vision.wallpapers.repository

import com.vision.wallpapers.api.alphaCoder.AlphaRetrofit
import com.vision.wallpapers.api.pexels.RetrofitInstance
import com.vision.wallpapers.api.unsplash.UnsplashRetrofit

class WallpaperRepo {

    suspend fun getImages() = RetrofitInstance.api.getImages()

    suspend fun getUnsplashImages() = UnsplashRetrofit.api.getPhotosList()

    suspend fun searchUnsplash(query: String) = UnsplashRetrofit.api.searchPhotos(query, orientation = null, color = null)

    suspend fun getAlphaImages(method: String = "featured") =
        AlphaRetrofit.api.getPhotos(method = method)

    suspend fun searchAlphaImages(query: String) = AlphaRetrofit.api.searchPhotos(query = query)
}