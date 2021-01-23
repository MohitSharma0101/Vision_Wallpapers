package com.vision.wallpapers.model.alphaCoder

data class AlphaPhotoResponseItem(
        val id:Int,
        val width:Int,
        val height:Int,
        val file_type:String,
        val file_size:String,
        val url_image:String,
        val url_thumb:String,
        val url_page:String
) {
}