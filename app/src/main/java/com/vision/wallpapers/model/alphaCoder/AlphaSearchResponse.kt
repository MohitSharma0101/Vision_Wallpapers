package com.vision.wallpapers.model.alphaCoder

data class AlphaSearchResponse(
    val success: Boolean,
    val wallpapers: List<AlphaPhotoResponseItem>,
    val total_match: Int
)
