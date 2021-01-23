package com.vision.wallpapers.api.alphaCoder

import com.vision.wallpapers.api.alphaCoder.api.KEY
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponse
import retrofit2.Response
import retrofit2.http.Query

interface AlphaApi {

    suspend fun getPhotos(
            @Query("auth")
            auth:String = KEY,
            @Query("method")
            method:String = "newest",
            @Query("page")
            page:Int = 1
    ):Response<AlphaPhotoResponse>


}