package com.vision.wallpapers.model

data class WallpaperResponse(
    val page:Int,
    val page_no:Int,
    val wallpapers:MutableList<Wallpaper>,
    val nextPage:String
) {
}