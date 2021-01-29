package com.vision.wallpapers.api.alphaCoder

import com.vision.wallpapers.api.alphaCoder.api.KEY
import com.vision.wallpapers.model.alphaCoder.AlphaCategoryResponse
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaApi {

    @GET("get.php")
    suspend fun getPhotos(
            @Query("auth")
            auth:String = KEY,
            @Query("method")
            method:String = "highest_rated",
            @Query("page")
            page:Int = 1
    ):Response<AlphaPhotoResponse>

    @GET("get.php")
    suspend fun getCategoryList(
        @Query("auth")
        auth: String = KEY,
        @Query("method")
        method: String = "category_list"
    ): Response<AlphaCategoryResponse>


}