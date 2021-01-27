package com.vision.wallpapers.model.alphaCoder

import com.vision.wallpapers.model.Response

data class AlphaPhotoResponse(
        val success:Boolean,
        val wallpapers:List<AlphaPhotoResponseItem>,
        val is_last:Boolean?
)
