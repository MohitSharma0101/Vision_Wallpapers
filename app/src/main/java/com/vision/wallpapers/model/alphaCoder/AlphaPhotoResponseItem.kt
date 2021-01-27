package com.vision.wallpapers.model.alphaCoder

import com.vision.wallpapers.model.Response

 data class AlphaPhotoResponseItem(
         val id:Int,
         val width:Int,
         val height:Int,
         val file_type:String,
         val file_size:String,
         val url_image:String,
         val url_thumb:String,
         val url_page:String
 ):Response{
  override val idR: Int
   get() = id
  override val urlImage: String
   get() = url_image
  override val urlThumb: String
   get() = url_thumb

 }