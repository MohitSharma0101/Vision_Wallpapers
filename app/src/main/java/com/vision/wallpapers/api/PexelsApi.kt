package com.vision.wallpapers.api

import com.vision.wallpapers.api.Api.KEY
import com.vision.wallpapers.model.pexels.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsApi {

    @Headers("Authorization: $KEY")
    @GET("search")
    suspend fun getImages(
            @Query("query")
            query: String = "Harry Potter"
    ): Response<WallpaperResponse>

}