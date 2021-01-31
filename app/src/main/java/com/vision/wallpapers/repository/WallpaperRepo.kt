package com.vision.wallpapers.repository

import com.vision.wallpapers.api.alphaCoder.AlphaRetrofit
import com.vision.wallpapers.api.pexels.RetrofitInstance
import com.vision.wallpapers.api.unsplash.UnsplashRetrofit
import com.vision.wallpapers.database.WallpaperDatabase
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem

class WallpaperRepo(val db: WallpaperDatabase) {

    suspend fun getImages() = RetrofitInstance.api.getImages()

    suspend fun getUnsplashImages() = UnsplashRetrofit.api.getPhotosList()

    suspend fun searchUnsplash(query: String) = UnsplashRetrofit.api.searchPhotos(query, orientation = null, color = null)

    suspend fun getAlphaImages(method: String = "featured") =
            AlphaRetrofit.api.getPhotos(method = method)

    suspend fun searchAlphaImages(query: String) = AlphaRetrofit.api.searchPhotos(query = query)

    suspend fun saveWallpaper(wallpaper:AlphaPhotoResponseItem) = db.getWallpaperDao().save(wallpaper)
    suspend fun deleteWallpaper(wallpaper:AlphaPhotoResponseItem) = db.getWallpaperDao().deleteWallpaper(wallpaper)

    suspend fun isWallpaperSaved(id:Int) = db.getWallpaperDao().getWallpaperByTitle(id)

    fun getSavedWallpaper() = db.getWallpaperDao().getAllWallpaper()

    suspend fun deleteAllWallpaper() = db.getWallpaperDao().deleteAllWallpaper()
}