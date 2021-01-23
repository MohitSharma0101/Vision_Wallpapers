package com.vision.wallpapers.api.unsplash

import com.vision.wallpapers.model.unsplash.UnsplashResponse
import com.vision.wallpapers.model.unsplash.UnsplashSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos")
    suspend fun getPhotosList(
            @Query("page")
            page: Int = 1,
            @Query("per_page")
            per_page: Int = 30,
            @Query("order_by")
            order_by: String = "latest"
    ):Response<UnsplashResponse>

    @GET("search/photos")
    suspend fun searchPhotos(
            @Query("query")
            query: String,
            @Query("page")
            page: Int = 1,
            @Query("per_page")
            per_page: Int = 20,
            @Query("order_by")
            order_by: String = "relevant",
            @Query("color")
            color: String?,
            @Query("orientation")
            orientation: String?
    ): Response<UnsplashSearch>
}