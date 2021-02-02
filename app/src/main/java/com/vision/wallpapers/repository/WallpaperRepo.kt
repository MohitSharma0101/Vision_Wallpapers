package com.vision.wallpapers.repository

import com.vision.wallpapers.api.alphaCoder.AlphaRetrofit
import com.vision.wallpapers.api.unsplash.UnsplashRetrofit
import com.vision.wallpapers.database.WallpaperDatabase
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem

class WallpaperRepo(val db: WallpaperDatabase) {

    suspend fun getUnsplashImages() = UnsplashRetrofit.api.getPhotosList()

    suspend fun searchUnsplash(query: String) = UnsplashRetrofit.api.searchPhotos(query, orientation = null, color = null)

    suspend fun getAlphaImages(method: String = "featured", page: Int) =
        AlphaRetrofit.api.getPhotos(method = method, page = page)

    suspend fun searchAlphaImages(query: String, page: Int) =
        AlphaRetrofit.api.searchPhotos(query = query, page = page)

    suspend fun saveWallpaper(wallpaper:AlphaPhotoResponseItem) = db.getWallpaperDao().save(wallpaper)
    suspend fun deleteWallpaper(wallpaper:AlphaPhotoResponseItem) = db.getWallpaperDao().deleteWallpaper(wallpaper)

    suspend fun isWallpaperSaved(id:Int) = db.getWallpaperDao().getWallpaperByTitle(id)

    fun getSavedWallpaper() = db.getWallpaperDao().getAllWallpaper()

    suspend fun deleteAllWallpaper() = db.getWallpaperDao().deleteAllWallpaper()
}