package com.vision.wallpapers.model.alphaCoder

data class AlphaPhotoResponse(
    val success: Boolean,
    val wallpapers: MutableList<AlphaPhotoResponseItem>,
    val is_last: Boolean?
)
