package com.vision.wallpapers.model.alphaCoder

data class AlphaPhotoResponse(
        val success:Boolean,
        val photos:MutableList<AlphaPhotoResponseItem>,
        val is_last:Boolean?
)
