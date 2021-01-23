package com.vision.wallpapers.model.unsplash

data class UnsplashSearch(
        val results: List<Result>,
        val total: Int,
        val total_pages: Int
)