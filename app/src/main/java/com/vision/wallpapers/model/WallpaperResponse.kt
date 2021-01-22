package com.vision.wallpapers.model

data class WallpaperResponse(
        val page: Int,
        val per_page: Int,
        val photos: MutableList<Wallpaper>,
        val next_page: String
)