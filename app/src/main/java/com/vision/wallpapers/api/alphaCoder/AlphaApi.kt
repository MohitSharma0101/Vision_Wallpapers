package com.vision.wallpapers.api.alphaCoder

import com.vision.wallpapers.api.alphaCoder.api.KEY
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponse
import com.vision.wallpapers.model.alphaCoder.AlphaSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaApi {

    @GET("get.php")
    suspend fun getPhotos(
        @Query("auth")
        auth: String = KEY,
        @Query("method")
        method: String = "featured",
        @Query("page")
        page: Int = 1
    ):Response<AlphaPhotoResponse>

    @GET("get.php")
    suspend fun searchPhotos(
        @Query("auth")
        auth: String = KEY,
        @Query("method")
        method: String = "search",
        @Query("term")
        query: String,
        @Query("page")
        page: Int = 1
    ): Response<AlphaSearchResponse>


}