package com.vision.wallpapers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vision.wallpapers.model.pexels.WallpaperResponse
import com.vision.wallpapers.model.unsplash.UnsplashResponse
import com.vision.wallpapers.repository.WallpaperRepo
import com.vision.wallpapers.util.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class WallpaperViewModel(private val wallpaperRepo: WallpaperRepo): ViewModel() {

    val curatedWallpapers: MutableLiveData<Resources<WallpaperResponse>> = MutableLiveData()

    val unsplashPhotos: MutableLiveData<Resources<UnsplashResponse>> = MutableLiveData()

    val unsplashSearchPhotos: MutableLiveData<Resources<UnsplashResponse>> = MutableLiveData()


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

    fun searchUnsplashPhotos(query: String) = viewModelScope.launch {
        unsplashSearchPhotos.postValue(Resources.Loading())
        val response = wallpaperRepo.searchUnsplash(query)
        unsplashSearchPhotos.postValue(handleUnsplashPhotosSearchResponse(response))
    }

    private fun handleUnsplashPhotosSearchResponse(response: Response<UnsplashResponse>): Resources<UnsplashResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

}