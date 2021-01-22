package com.vision.wallpapers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vision.wallpapers.model.WallpaperResponse
import com.vision.wallpapers.repository.WallpaperRepo
import com.vision.wallpapers.util.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class WallpaperViewModel(private val wallpaperRepo: WallpaperRepo): ViewModel() {


    private val curatedWallpapers: MutableLiveData<Resources<WallpaperResponse>> = MutableLiveData()

//    fun getCuratedWallpapers() = viewModelScope.launch {
//        curatedWallpapers.postValue(Resources.Loading())
//        val response = wallpaperRepo.getImages()
//        curatedWallpapers.postValue(handelCuratedWallpaperResponse(response))
//    }
//
//    private fun handelCuratedWallpaperResponse(reponse: Response<WallpaperResponse>): Resources<WallpaperResponse>? {
//        if (reponse.isSuccessful){
//            reponse.body()?.let {
//
//            }
//        }
//
//    }


}