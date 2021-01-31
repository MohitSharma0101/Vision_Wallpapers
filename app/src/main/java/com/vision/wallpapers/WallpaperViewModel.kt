package com.vision.wallpapers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponse
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem
import com.vision.wallpapers.model.alphaCoder.AlphaSearchResponse
import com.vision.wallpapers.model.pexels.WallpaperResponse
import com.vision.wallpapers.model.unsplash.UnsplashResponse
import com.vision.wallpapers.model.unsplash.UnsplashSearch
import com.vision.wallpapers.repository.WallpaperRepo
import com.vision.wallpapers.util.Constants
import com.vision.wallpapers.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class WallpaperViewModel(private val wallpaperRepo: WallpaperRepo): ViewModel() {

    val curatedWallpapers: MutableLiveData<Resources<WallpaperResponse>> = MutableLiveData()

    val unsplashPhotos: MutableLiveData<Resources<UnsplashResponse>> = MutableLiveData()
    val alphaPhoto: MutableLiveData<Resources<AlphaPhotoResponse>> = MutableLiveData()
    val alphaSearchPhotos: MutableLiveData<Resources<AlphaSearchResponse>> = MutableLiveData()

    val unsplashSearchPhotos: MutableLiveData<Resources<UnsplashSearch>> = MutableLiveData()
    var favWallpaper : LiveData<List<AlphaPhotoResponseItem>>

    var method = Constants.HIGH_RATED

    init {
        favWallpaper = getSavedWallpaper()
        getAlphaPhotos()
    }

    fun deleteAllWallpaper() = viewModelScope.launch {
        wallpaperRepo.deleteAllWallpaper()
    }
    fun saveWallpaper(wallpaper: AlphaPhotoResponseItem) = viewModelScope.launch {
        wallpaperRepo.saveWallpaper(wallpaper)
    }

    fun deleteWallpaper(wallpaper: AlphaPhotoResponseItem) = viewModelScope.launch {
        wallpaperRepo.deleteWallpaper(wallpaper)
    }

    fun getSavedWallpaper(): LiveData<List<AlphaPhotoResponseItem>> {
        Log.d("mohit", "called")
        return wallpaperRepo.getSavedWallpaper()
    }


    suspend fun isWallpaperSaved(wallpaper: AlphaPhotoResponseItem): Boolean {
        var a: AlphaPhotoResponseItem? = null
        val job = viewModelScope.launch(Dispatchers.IO) {
            a = wallpaperRepo.isWallpaperSaved(wallpaper.id)
        }
        job.join()
        return a != null
    }


    fun getCuratedWallpapers() = viewModelScope.launch {
        curatedWallpapers.postValue(Resources.Loading())
        val response = wallpaperRepo.getImages()
        curatedWallpapers.postValue(handleCuratedWallpaperResponse(response))
    }

    private fun handleCuratedWallpaperResponse(response: Response<WallpaperResponse>): Resources<WallpaperResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    fun getUnsplashPhotos() = viewModelScope.launch {
        unsplashPhotos.postValue(Resources.Loading())
        val response = wallpaperRepo.getUnsplashImages()
        unsplashPhotos.postValue(handleUnsplashPhotosResponse(response))
    }

    private fun handleUnsplashPhotosResponse(response: Response<UnsplashResponse>): Resources<UnsplashResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    fun getAlphaPhotos(method: String = "featured") = viewModelScope.launch {
        alphaPhoto.postValue(Resources.Loading())
        val response = wallpaperRepo.getAlphaImages(method)
        alphaPhoto.postValue(handleAlphaPhotosResponse(response))
    }

    private fun handleAlphaPhotosResponse(response: Response<AlphaPhotoResponse>): Resources<AlphaPhotoResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }


    fun searchUnsplashPhotos(query: String) = viewModelScope.launch {
        unsplashSearchPhotos.postValue(Resources.Loading())
        val response = wallpaperRepo.searchUnsplash(query)
        unsplashSearchPhotos.postValue(handleUnsplashPhotosSearchResponse(response))
    }

    private fun handleUnsplashPhotosSearchResponse(response: Response<UnsplashSearch>): Resources<UnsplashSearch> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    fun searchAlphaPhotos(query: String) = viewModelScope.launch {
        alphaSearchPhotos.postValue(Resources.Loading())
        val response = wallpaperRepo.searchAlphaImages(query)
        alphaSearchPhotos.postValue(handleSearchAlphaPhotosResponse(response))
    }

    private fun handleSearchAlphaPhotosResponse(response: Response<AlphaSearchResponse>): Resources<AlphaSearchResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }


}