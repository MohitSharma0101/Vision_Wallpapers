package com.vision.wallpapers.model.pexels

data class WallpaperResponse(
        val total_results: Int,
        val page: Int,
        val per_page: Int,
        val photos: MutableList<Wallpaper>,
        val next_page: String
)