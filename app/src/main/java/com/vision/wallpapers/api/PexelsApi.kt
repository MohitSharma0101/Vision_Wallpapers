package com.vision.wallpapers.api

import com.vision.wallpapers.api.Api.KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsApi {

    @Headers("Authorization:${KEY}")
    @GET("v1/curated")
    suspend fun getImages(
        @Query("per_page")
        perPage:Int = 15,
        @Query("page")
        pageNo:Int = 1
    )

}