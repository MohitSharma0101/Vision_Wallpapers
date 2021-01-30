package com.vision.wallpapers.model.pexels



data class Wallpaper(
        var id: Int? = null,
        val avg_color: String,
        val height: Int,
        val photographer: String,
        val photographer_id: Int,
        val photographer_url: String,
        val src: Src,
        val url: String,
        val width: Int
)