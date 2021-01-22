package com.vision.wallpapers.api

import com.vision.wallpapers.model.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PexelsApi {

    @Headers("Authorization: 563492ad6f91700001000001e0494e4454d04a8f8d4bbe4104025632")
    @GET("curated")
    suspend fun getImages(
//        @Query("per_page")
//        perPage:Int = 15,
//        @Query("page")
//        pageNo:Int = 1
    ): Response<WallpaperResponse>

}