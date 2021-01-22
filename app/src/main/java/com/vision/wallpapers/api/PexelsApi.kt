package com.vision.wallpapers.api

import retrofit2.http.GET

interface PexelsApi {

    @GET("v1/curated")
    suspend fun getImages(
        perPage:Int = 15,
        pageNo:Int = 1
    )

}