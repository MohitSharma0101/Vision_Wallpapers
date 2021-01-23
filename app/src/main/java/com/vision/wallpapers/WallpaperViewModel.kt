package com.vision.wallpapers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vision.wallpapers.model.pexels.WallpaperResponse
import com.vision.wallpapers.repository.WallpaperRepo
import com.vision.wallpapers.util.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class WallpaperViewModel(private val wallpaperRepo: WallpaperRepo): ViewModel() {

    val curatedWallpapers: MutableLiveData<Resources<WallpaperResponse>> = MutableLiveData()

//    init {
//        getCuratedWallpapers()
//    }

    fun getCuratedWallpapers() = viewModelScope.launch {
        curatedWallpapers.postValue(Resources.Loading())
        val response = wallpaperRepo.getImages()
        curatedWallpapers.postValue(handleCuratedWallpaperResponse(response))
    }

    private fun handleCuratedWallpaperResponse(repose: Response<WallpaperResponse>): Resources<WallpaperResponse> {
        if (repose.isSuccessful) {
            repose.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(repose.message())
    }


}